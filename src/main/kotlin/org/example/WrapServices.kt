package org.example

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object WrapServices {

    @JvmInline
    value class OptimizationLevel(val value: Int) {
        init {
            require(value > 0) { OPTIMIZATION_LEVEL_MUST_BE_POSITIVE }
        }
    }

    class Accumulator(val size: Int = 0, var value: Int = 0) {

        init {
            require(size > 0) { ACCUMULATOR_SIZE_MUST_BE_POSITIVE }
        }

        companion object {
            fun fromEntry(entry: WrapEntry) = Accumulator(size = entry.length() - ONE)
        }

        operator fun plusAssign(value: Int) {
            this.value = "${this.value}$value".toInt()
        }
    }

    suspend fun wrap(entry: WrapEntry, optimizationLevel: OptimizationLevel? = null): WrapResult = coroutineScope {
        when (entry.length()) {
            0 -> WrapResult.EMPTY
            1 -> WrapResult.single(entry.value)
            else -> optimizationLevel?.let { execOptimized(entry, it.value) } ?: execSimple(entry)
        }
    }

    private fun execSimple(entry: WrapEntry) = with(Accumulator.fromEntry(entry)) { ->
        entry.toList()
            .mapIndexedNotNull { index, item ->
                (sum(value.toString()) + item).let { sum ->
                    if (sum > SIZE) {
                        "$value".also { value = item }
                    } else {
                        this += item
                        if (entry.isLast(index)) this.value.toString() else null
                    }
                }
            }.let { WrapResult.fromList(it) }
    }

    private suspend fun execOptimized(entry: WrapEntry, level: Int): WrapResult = coroutineScope {
        with(Channel<WrapResult>(capacity = level)) {
            IntRange(1, level).let {
                it.map { launch { send(doExecOptimized(entry.shuffled())) } }.forEach { job -> job.join() }
                it.map { receive() }.maxBy { result -> result.distribution() }
            }
        }
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
