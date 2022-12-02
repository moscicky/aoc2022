package day2

import java.io.File


//A for Rock, B for Paper, and C for Scissors
//(1 for Rock, 2 for Paper, and 3 for Scissors)
// plus the score for the outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won).

// X means you need to lose, Y means you need to end the round in a draw, and Z means you
fun main() {

    val path = "src/main/kotlin/day2/input2.txt"
    var score = 0

    File(path).useLines {
        for (line in it) {
            val moves = line.toCharArray()
            val them = moves[0]
            val us = moves[2]

            if (us == 'X') { // loose
                score += 0
                when (them) {
                    'A' -> score += 3 //Scissors
                    'B' -> score += 1 // Rock
                    'C' -> score += 2 // Paper
                }
            } else if (us == 'Y') { // draw
                score += 3
                when (them) {
                    'A' -> score += 1 // Rock
                    'B' -> score += 2 // Paper
                    'C' -> score += 3 //Scissors
                }
            } else if (us == 'Z') { // win
                score += 6
                when (them) {
                    'A' -> score += 2 // Paper
                    'B' -> score += 3 // Scissors
                    'C' -> score += 1 // Rock
                }
            }
        }
    }
    println(score)
}