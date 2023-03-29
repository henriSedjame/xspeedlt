package org.example

data class WrapDistributionItem(val content: Int, var itemNumber: Int = 1) : Comparable<WrapDistributionItem> {
    override fun compareTo(other: WrapDistributionItem): Int =
        Comparator
            .comparing<WrapDistributionItem, Int> { it.content }
            .thenComparing<Int> {  it.itemNumber }
            .compare(this, other)

}
data class WrapDistribution(val items: List<WrapDistributionItem>): Comparable<WrapDistribution> {
    override fun compareTo(other: WrapDistribution): Int =
        Comparator
            .comparing<WrapDistribution, Int> { it.items.size }
            .reversed()
            .run {
                var c: Comparator<WrapDistribution> = this
                (items.indices).forEach { i ->
                    c = c.thenComparing<WrapDistributionItem> { it.items[i] }
                }

                c
            }.compare(this, other)

}