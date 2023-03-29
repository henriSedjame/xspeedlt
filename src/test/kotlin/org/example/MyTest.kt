package org.example


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MyTest {

    @Test
    fun testOptimizedPack() {
        val entry = PackEntry("163841689525773")
        pack(entry, optimized = true).let {
            assertTrue (it.matchWith(entry) )
            assertEquals(8, it.size())
        }
    }

    @Test
    fun testSimplePack() {
        val entry = PackEntry("163841689525773")
        pack(entry).let {
            assertTrue (it.matchWith(entry) )
            assertEquals(10, it.size())
        }
    }
}