package org.example

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class WrapResultDistributionTest {

    @Test
    fun `compare distribution1 equals to distribution2`() {

        val d1 = listOf(
             WrapResult.Distribution.Item(content=10, itemNumber=6),
             WrapResult.Distribution.Item(content=9, itemNumber=1),
             WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val d2 = listOf(
             WrapResult.Distribution.Item(content=10, itemNumber=6),
             WrapResult.Distribution.Item(content=9, itemNumber=1),
             WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val wd1 = WrapResult.Distribution(d1)
        val wd2 = WrapResult.Distribution(d2)

        assertTrue(wd1 == wd2)
    }

    @Test
    fun `compare distribution1 higher than distribution2`() {

        val d1 = listOf(
            WrapResult.Distribution.Item(content=10, itemNumber=6),
            WrapResult.Distribution.Item(content=9, itemNumber=1),
            WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val d2 = listOf(
            WrapResult.Distribution.Item(content=10, itemNumber=5),
            WrapResult.Distribution.Item(content=9, itemNumber=2),
            WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val wd1 = WrapResult.Distribution(d1)
        val wd2 = WrapResult.Distribution(d2)

        assertTrue(wd1 > wd2)
    }

    @Test
    fun `compare distribution1 and distribution2 with different size`() {

        val d1 = listOf(
            WrapResult.Distribution.Item(content=9, itemNumber=1),
            WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val d2 = listOf(
            WrapResult.Distribution.Item(content=10, itemNumber=5),
            WrapResult.Distribution.Item(content=9, itemNumber=2),
            WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val wd1 = WrapResult.Distribution(d1)
        val wd2 = WrapResult.Distribution(d2)

        assertTrue(wd1 > wd2)
    }

    @Test
    fun `compare distribution2 higher than distribution1`() {

        val d1 = listOf(
            WrapResult.Distribution.Item(content=10, itemNumber=6),
            WrapResult.Distribution.Item(content=9, itemNumber=1),
            WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val d2 = listOf(
            WrapResult.Distribution.Item(content=10, itemNumber=6),
            WrapResult.Distribution.Item(content=9, itemNumber=2),
            WrapResult.Distribution.Item(content=6, itemNumber=1)
        )

        val wd1 = WrapResult.Distribution(d1)
        val wd2 = WrapResult.Distribution(d2)

        assertTrue(wd1 < wd2)
    }
}