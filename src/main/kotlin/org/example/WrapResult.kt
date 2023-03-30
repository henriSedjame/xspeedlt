package org.example

/**
 * wrap result representation
 * @param value the value of the result
 */
@JvmInline
value class WrapResult(private val value: String) {

    /**
     * Represent a distribution of the result
     * @param items the list of itemDistribution
     */
    data class Distribution(val items: List<Item>): Comparable<Distribution> {

        /**
         * Represent an item distribution
         * @param content the distinct size of each result item
         * @param itemNumber the number of result item with the same content
         */
        data class Item(val content: Int, var itemNumber: Int = 1) : Comparable<Item> {
            override fun compareTo(other: Item): Int =
                Comparator
                    .comparing<Item, Int> {  it.content }
                    .thenComparing<Int> {  it.itemNumber }
                    .compare(this, other)

            fun sum() = content * itemNumber
        }
        override fun compareTo(other: Distribution): Int =
            Comparator
                .comparing<Distribution, Int> { it.items.size }
                .reversed()
                .run {
                    var comparator: Comparator<Distribution> = this

                    items.indices.forEach { i ->
                        comparator = comparator.thenComparing<Item> { it.items[i] }
                    }

                    comparator
                }.compare(this, other)

        /**
         * check if a given result match with the distribution
         */
        fun matchWith(result: WrapResult): Boolean = with(sum(result.value.replace(SEPARATOR, EMPTY_STR))) {
            items.sumOf { it.sum() } == this
        }
    }

    companion object {
        /**
         * @return wrap result with empty string value
         */
        val EMPTY = WrapResult(EMPTY_STR)

        /**
         * @return a wrap result with a single value
         */
        fun single(value: String): WrapResult {
            require(value.length == ONE) { SINGLE_DIGIT_EXPECTED }
            return WrapResult(value)
        }

        /**
         * @param list a list of string
         * @return a wrap result from a list of string
         *
         */
        fun fromList(list: List<String>) = WrapResult(list.joinToString(SEPARATOR))
    }

    /**
     * @return the size of the result value, 0 if value is empty
     */
    fun size() = if (value.isEmpty()) 0 else value.split(SEPARATOR).size

    /**
     * check a given entry match with the result
     */
    fun matchWith(entry: WrapEntry): Boolean = with(entry.value.sorted { filter { it.isNotEmpty() } }) {
         this == value.replace(SEPARATOR, EMPTY_STR).sorted()
    }

    /**
     * calculate the distribution of the result
     */
    fun distribution(): Distribution = with(mutableListOf<Distribution.Item>()) {
       this@WrapResult.toList()
            .forEach { sum ->
                    firstOrNull { item -> item.content == sum }
                        ?.let { item -> item.itemNumber ++ }
                        ?: add(Distribution.Item(sum))
            }
        Distribution(this.sorted().reversed()).also {
            require(it.matchWith(this@WrapResult)) { "distribution doesn't match with result" }
        }
    }

    /**
     * @return the list of sum of each result's item
     */
    private fun toList() = value.split(SEPARATOR).map { sum(it) }
}