package org.example

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class WrapDistributionTest {

    @Test
    fun `compare distribution1 equals to distribution2`() {

        val d1 = listOf(
            WrapDistributionItem(content=10, itemNumber=6),
            WrapDistributionItem(content=9, itemNumber=1),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val d2 = listOf(
            WrapDistributionItem(content=10, itemNumber=6),
            WrapDistributionItem(content=9, itemNumber=1),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val wd1 = WrapDistribution(d1)
        val wd2 = WrapDistribution(d2)

        assertTrue(wd1 == wd2)
    }

    @Test
    fun `compare distribution1 higher than distribution2`() {

        val d1 = listOf(
            WrapDistributionItem(content=10, itemNumber=6),
            WrapDistributionItem(content=9, itemNumber=1),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val d2 = listOf(
            WrapDistributionItem(content=10, itemNumber=5),
            WrapDistributionItem(content=9, itemNumber=2),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val wd1 = WrapDistribution(d1)
        val wd2 = WrapDistribution(d2)

        assertTrue(wd1 > wd2)
    }

    @Test
    fun `compare distribution1 and distribution2 with different size`() {

        val d1 = listOf(
            WrapDistributionItem(content=9, itemNumber=1),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val d2 = listOf(
            WrapDistributionItem(content=10, itemNumber=5),
            WrapDistributionItem(content=9, itemNumber=2),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val wd1 = WrapDistribution(d1)
        val wd2 = WrapDistribution(d2)

        assertTrue(wd1 > wd2)
    }

    @Test
    fun `compare distribution2 higher than distribution1`() {

        val d1 = listOf(
            WrapDistributionItem(content=10, itemNumber=6),
            WrapDistributionItem(content=9, itemNumber=1),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val d2 = listOf(
            WrapDistributionItem(content=10, itemNumber=6),
            WrapDistributionItem(content=9, itemNumber=2),
            WrapDistributionItem(content=6, itemNumber=1)
        )

        val wd1 = WrapDistribution(d1)
        val wd2 = WrapDistribution(d2)

        assertTrue(wd1 < wd2)
    }
}