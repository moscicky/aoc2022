package day3

import java.io.File
import java.lang.IllegalStateException

fun main() {
    val path = "src/main/kotlin/day3/input2.txt"
    var score = 0

    val rucksacks = mutableListOf<String>()

    File(path).useLines {
        for (line in it) rucksacks.add(line)
    }

    for (rucksack in rucksacks) {
        val itemsCount = rucksack.length / 2
        val first = rucksack.subSequence(0, itemsCount).toSet()
        val second = rucksack.subSequence(itemsCount, rucksack.length).toSet()

        val overlapping = first.intersect(second)
        for (char in overlapping) {
            val weight = if (char.isUpperCase()) {
                (char - 'A' + 27)
            } else {
                (char - 'a' + 1)
            }
            score += weight
        }
    }
    println(score)

    var i = 0
    val k = 3
    var score2 = 0
    while (i < rucksacks.size) {
        var overlapping = mutableSetOf<Char>()
        for (j in 0 until k) {
            if (j == 0) overlapping.addAll(rucksacks[i+j].toSet())
            else overlapping = overlapping.intersect(rucksacks[i+j].toSet()).toMutableSet()
        }
        if (overlapping.size != 1) throw IllegalStateException("Badge is not unique")
        for (char in overlapping) {
            val weight = if (char.isUpperCase()) {
                (char - 'A' + 27)
            } else {
                (char - 'a' + 1)
            }
            score2 += weight
        }
        i += k
    }

    println(score2)
}
