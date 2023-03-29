package org.example


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MyTest {

    @Test
    fun `try to pack empty entry`() {
        val entry = PackEntry("")
        pack(entry).let {
            assertTrue (it.matchWith(entry) )
            assertEquals(0, it.size())
        }
    }


    @Test
    fun `pack entry with one item`() {
        val entry = PackEntry("1")
        pack(entry).let {
            assertTrue (it.matchWith(entry) )
            assertEquals(1, it.size())
        }
    }

    @Test
    fun `optimized pack entry`() {
        val entry = PackEntry("163841689525773")
        pack(entry, optimized = true).let {
            assertTrue (it.matchWith(entry) )
            assertEquals(8, it.size())
        }
    }

    @Test
    fun `simple pack entry`() {
        val entry = PackEntry("163841689525773")
        pack(entry).let {
            assertTrue (it.matchWith(entry) )
            assertEquals(10, it.size())
        }
    }
}