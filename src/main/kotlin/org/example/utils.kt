package org.example

class Accumulator(val size: Int = 0, var value: Int = 0) {
    operator fun plusAssign(value: Int) {
        this.value = "${this.value}$value".toInt()
    }
}
fun sum(entry: String) = entry.split("").filter { it.isNotEmpty() }.sumOf { it.toInt() }

