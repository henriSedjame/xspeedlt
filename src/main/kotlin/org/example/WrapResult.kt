package org.example


@JvmInline
value class WrapResult(private val value: String) {
    companion object {
        val EMPTY = WrapResult("")
        fun fromList(list: List<String>) = WrapResult(list.joinToString(SEPARATOR))
    }

    fun size() = if (value.isEmpty()) 0 else value.split(SEPARATOR).size

    fun matchWith(entry: WrapEntry): Boolean {
        val result = value.replace(SEPARATOR, "").split("").sorted().joinToString("")
        val expected = entry.value.split("").filter { it.isNotEmpty() }.sorted().joinToString("")
        return result == expected
    }

    fun distributions(): WrapDistribution = with(mutableListOf<WrapDistributionItem>()) {
        value.split(SEPARATOR)
            .forEach {
                sum(it).let { sum ->
                    firstOrNull { p -> p.content == sum }
                        ?.let { p -> p.itemNumber++ }
                        ?: add(WrapDistributionItem(sum))
                }
            }
        WrapDistribution(this.sorted().reversed())
    }
}