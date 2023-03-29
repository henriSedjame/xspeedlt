package org.example

@JvmInline
value class WrapEntry(val value: String) {
    init {
        require(value.toCharArray().all { it.isDigit() })
    }

    fun length() = value.length

    fun toList() = value.split("").filter { it.isNotEmpty() }.map { it.toInt() }

    fun isLast(index: Int) = index == value.length - 1

    fun buildAccumulator() = Accumulator(size = length() - 1)

    fun shuffle() : WrapEntry = WrapEntry(value.split("").shuffled().joinToString(""))
}