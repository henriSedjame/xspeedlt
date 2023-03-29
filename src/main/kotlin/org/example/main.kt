package org.example


const val SEPARATOR = "/"
const val SIZE = 10

class Accumulator(val size: Int = 0, var value: Int = 0) {
    operator fun plusAssign(value: Int) {
        this.value = "${this.value}$value".toInt()
    }
}

@JvmInline
value class PackEntry(val value: String) {
    init {
        require(value.toCharArray().all { it.isDigit() })
    }

    fun length() = value.length

    fun toList() = value.split("").filter { it.isNotEmpty() }.map { it.toInt() }

    fun isLast(index: Int) = index == value.length - 1

    fun buildAccumulator() = Accumulator(size = length() - 1)
}

@JvmInline
value class PackResult(private val value: String) {
    companion object {
        val EMPTY = PackResult("")
        fun fromList(list: List<String>) = PackResult(list.joinToString(SEPARATOR))
    }

    fun size() = if (value.isEmpty()) 0 else value.split(SEPARATOR).size

    fun matchWith(entry: PackEntry): Boolean {
        println(value)
        val result = value.replace(SEPARATOR, "").split("").sorted().joinToString("")
        val expected = entry.value.split("").filter { it.isNotEmpty() }.sorted().joinToString("")

        return result == expected
    }
}

fun pack(entry: PackEntry, optimized: Boolean = false): PackResult =
    when (entry.length()) {
        0 -> PackResult.EMPTY
        1 -> PackResult.fromList(listOf(entry.value))
        else -> if (optimized) execOptimized(entry) else execSimple(entry)
    }

private fun execSimple(entry: PackEntry) = with(entry.buildAccumulator()) { ->
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
        }.let { PackResult.fromList(it) }
}

private fun execOptimized(entry: PackEntry): PackResult {
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

    return PackResult.fromList(result)
}

private fun sum(entry: String) = entry.split("").filter { it.isNotEmpty() }.sumOf { it.toInt() }

