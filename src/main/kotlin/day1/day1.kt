package day1

import java.io.File
import java.util.PriorityQueue

fun addToHeap(queue: PriorityQueue<Int>, element: Int, heapSize: Int) {
    queue.add(element)
    if (queue.size == heapSize + 1) {
        queue.poll()
    }
}


// time: O(n log k)
// space O(k)
fun main() {
    val path = "src/main/kotlin/day1/input2.txt"
    val k = 3
    var current = 0
    val queue = PriorityQueue<Int> { o1, o2 -> o1 - o2 }

    File(path).useLines {
        for (line in it) {
            if (line == "") {
                addToHeap(queue, current, k)
                current = 0
            } else {
                current += line.trim().toInt()
            }
        }
    }

    addToHeap(queue, current, k)

    val result = queue.stream().reduce { a, b -> a + b }.get()

    println(result)
}