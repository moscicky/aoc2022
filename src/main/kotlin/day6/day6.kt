package day6

import java.io.File

fun main() {
    val path = "src/main/kotlin/day6/input1.txt"

    println(answer("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
    println(answer("bvwbjplbgvbhsrlpgdmjqwftvncz"))
    println(answer("nppdvjthqldpwncqszvftbrmjlhg"))
    println(answer("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
    println(answer("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))

    File(path).useLines {
        for (line in it) {
            println(answer(line))
        }
    }
}


fun answer(s: String): Int {
    val window = mutableMapOf<Char, Int>()
    val k = 14
    var windowStart = 0
    for (i in 0..s.lastIndex) {
        val char = s[i]

        if (char in window) {
            windowStart = Math.max(windowStart, window[char]!! + 1)
        }

        window[char] = i

        if (i - windowStart + 1 == k) {
            return i + 1
        }
    }
    return -1
}