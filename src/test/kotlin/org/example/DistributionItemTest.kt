package org.example

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DistributionItemTest {

    @Test
    fun `compare with same contents and same itemNumbers`() {
        val d1 = WrapResult.Distribution.Item(content=10, itemNumber=5)
        val d2 = WrapResult.Distribution.Item(content=10, itemNumber=5)
        assertTrue(d1 == d2)
    }

    @Test
    fun `compare with same contents and different itemNumbers`() {
        val d1 = WrapResult.Distribution.Item(content=10, itemNumber=2)
        val d2 = WrapResult.Distribution.Item(content=10, itemNumber=5)
        assertTrue(d1 < d2)
    }

    @Test
    fun `compare with different contents and different itemNumbers`() {
        val d1 = WrapResult.Distribution.Item(content=10, itemNumber=5)
        val d2 = WrapResult.Distribution.Item(content=9, itemNumber=2)
        assertTrue(d1 > d2)
    }

    @Test
    fun `compare with different contents and same itemNumbers`() {
        val d1 = WrapResult.Distribution.Item(content=10, itemNumber=5)
        val d2 = WrapResult.Distribution.Item(content=9, itemNumber=5)
        assertTrue(d1 > d2)
    }
}