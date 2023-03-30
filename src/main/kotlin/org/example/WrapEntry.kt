package org.example

/**
 * wrap entry representation
 * @param value the value of the entry
 */
@JvmInline
value class WrapEntry(val value: String) {
    init {
        require(value.toCharArray().all { it.isDigit() }){ STRING_OF_DIGITS_EXPECTED }
    }

    /**
     * @return the length of the entry
     */
    fun length() = value.length

    /**
     * @return the list of number representation of each entry's character
     */
    fun toList() = value.split(EMPTY_STR).filter { it.isNotEmpty() }.map { it.toInt() }

    /**
     * Check if a given index is the last one
     * @param index the index to check
     */
    fun isLast(index: Int) = index == value.length - ONE

    /**
     * @return a shuffled version of the entry
     */
    fun shuffled() : WrapEntry = WrapEntry(value.shuffled())
}