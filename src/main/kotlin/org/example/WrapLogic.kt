package org.example

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


const val OPTIN_LEVEL = 100
const val SEPARATOR = "/"
const val SIZE = 10

object WrapLogic {
    suspend fun wrap(entry: WrapEntry, optimized: Boolean = false): WrapResult = coroutineScope {
        when (entry.length()) {
            0 -> WrapResult.EMPTY
            1 -> WrapResult.fromList(listOf(entry.value))
            else -> if (optimized) execOptimized(entry) else execSimple(entry)
        }
    }

    private fun execSimple(entry: WrapEntry) = with(entry.buildAccumulator()) { ->
        entry.toList()
            .mapIndexedNotNull { index, item ->
                (sum(value.toString()) + item).let { v ->
                    if (v > SIZE) {
                        "$value".also { value = item }
                    } else {
                        this += item

                        if (entry.isLast(index)) this.value.toString() else null
                    }
                }
            }.let { WrapResult.fromList(it) }
    }

    private suspend fun execOptimized(entry: WrapEntry): WrapResult = coroutineScope {
        val channel = Channel<WrapResult>(capacity = OPTIN_LEVEL)

        val range = IntRange(1, OPTIN_LEVEL)

        range.map { launch { doExecOptimized(entry.shuffle()).let { channel.send(it) } } }.forEach { it.join() }

        range.map { channel.receive() }.maxBy { it.distributions() }
    }

    private suspend fun doExecOptimized(entry: WrapEntry): WrapResult = coroutineScope {
        var remaining = entry.toList().toMutableList()
        val result = mutableListOf<String>()

        while (remaining.isNotEmpty()) {

            // get first remaining item
            val firstRemaining = remaining.first()

            var nextItem = "$firstRemaining"

            // remove first remaining item from remaining list
            remaining = remaining.drop(1).toMutableList()


            do {
                // set a max value to avoid infinite loop
                var max = SIZE

                // set a second item to add at null
                var second: Int? = null

                // find a second item to add
                while (second == null && max > 0 && sum(nextItem) < max) {
                    // find a second item where sum is equal to max
                    second = remaining.firstOrNull {
                        it + sum(nextItem) == max
                    }
                    // decrement max
                    max--
                }

                // if found, add second item to next item
                // otherwise break
                if (second != null) {
                    // remove second item from remaining list
                    remaining = (remaining - second).toMutableList()
                    // add second item to next item
                    nextItem += second
                    // reset max value
                    max = SIZE
                } else {
                    break
                }

            } while (remaining.isNotEmpty() && sum(nextItem) < SIZE)

            result.add(nextItem)
        }

        WrapResult.fromList(result)
    }
}
