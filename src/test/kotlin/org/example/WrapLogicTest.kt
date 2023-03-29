package org.example


import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WrapLogicTest {

    @Test
    fun `try to pack empty entry`() {
        val entry = WrapEntry("")
        runBlocking {  WrapLogic.wrap(entry) }.let {
            assertTrue (it.matchWith(entry) )
            assertEquals(0, it.size())
        }
    }


    @Test
    fun `pack entry with one item`() {
        val entry = WrapEntry("1")
        runBlocking {  WrapLogic.wrap(entry) }.let {
            assertTrue (it.matchWith(entry) )
            assertEquals(1, it.size())
        }
    }

    @Test
    fun `optimized pack entry`() {
        val entry = WrapEntry("163841689525773")
        runBlocking {
            WrapLogic.wrap(entry, optimized = true)
        }.let {
            assertTrue (it.matchWith(entry) )
            assertEquals(8, it.size())
        }
    }

    @Test
    fun `simple pack entry`() {
        val entry = WrapEntry("163841689525773")
        runBlocking {  WrapLogic.wrap(entry) }.let {
            assertTrue (it.matchWith(entry) )
            assertEquals(10, it.size())
        }
    }

}