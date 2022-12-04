package day4

import java.io.File

fun main() {
    val path = "src/main/kotlin/day4/input.txt"
    var score = 0
    var score2 = 0

    File(path).useLines {
        for (line in it) {
            val tmp = line.split(",")
            val first = tmp[0].split("-")
            val second = tmp[1].split("-")

            val firstLeft = first[0].toInt()
            val firstRight = first[1].toInt()

            val secondLeft = second[0].toInt()
            val secondRight = second[1].toInt()


            if ((firstLeft <= secondLeft && firstRight >= secondRight)
                || (secondLeft <= firstLeft && secondRight >= firstRight)) score++

            if ((secondLeft in firstLeft..firstRight) ||
                (firstLeft in secondLeft..secondRight)) score2++
        }

    }
    println(score)
    println(score2)
}