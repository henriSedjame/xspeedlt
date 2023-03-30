package org.example

/**
 * return sum of all digits in entry
 */
fun sum(entry: String) = entry.split(EMPTY_STR).filter { it.isNotEmpty() }.sumOf { it.toInt() }

/**
 * @return a [WrapServices.OptimizationLevel] from an [Int]
 */
fun Int.toOptimizationLevel() = WrapServices.OptimizationLevel(this)

/**
 * @return a shuffled version of a string
 */
fun String.shuffled(): String = this.split(EMPTY_STR).shuffled().joinToString(EMPTY_STR)

/**
 * @return a sorted version of a string
 */
fun String.sorted(block : List<String>.() -> List<String> = { this }): String = this.split(EMPTY_STR).run {
    block()
}.sorted().joinToString(EMPTY_STR)