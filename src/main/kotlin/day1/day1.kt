package day1

import java.io.File
import java.util.PriorityQueue
import kotlin.random.Random

fun addToHeap(queue: PriorityQueue<Int>, element: Int, heapSize: Int) {
    queue.add(element)
    if (queue.size == heapSize + 1) {
        queue.poll()
    }
}

// time: O(n log k)
// space O(k)
fun heapSolution() {
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

// average time: O(N)
fun quickSelectSolution() {
    val k = 3
    val path = "src/main/kotlin/day1/input2.txt"
    var current = 0
    val counts = mutableListOf<Int>()

    File(path).useLines {
        for (line in it) {
            if (line == "") {
                counts.add(current)
                current = 0
            } else {
                current += line.trim().toInt()
            }
        }
    }

    counts.add(current)

    quickSelect(counts, 0, counts.size - 1, counts.size - k)

    var result = 0
    for (i in counts.size - k until counts.size) {
        result += counts[i]
    }

    println(result)
}

fun swap(arr: MutableList<Int>, from: Int, to: Int) {
    val tmp = arr[from]
    arr[from] = arr[to]
    arr[to] = tmp
}

fun partition(arr: MutableList<Int>, from: Int, to: Int): Int {
    val pivot = arr[to]
    var left = from
    for (i in from until to) {
        if (arr[i] < pivot) {
            swap(arr, left, i)
            left++
        }
    }

    swap(arr, left, to)
    return left
}

fun quickSelect(counts: MutableList<Int>, from: Int, to: Int, wantedIdx: Int): MutableList<Int> {
    val pivotIdx = Random.nextInt(from, to + 1)

    swap(counts, pivotIdx, to)

    val partitionIdx = partition(counts, from, to)

    return if (partitionIdx == wantedIdx) {
        counts
    } else if (partitionIdx < wantedIdx) {
        quickSelect(counts, partitionIdx + 1, to, wantedIdx);
    } else {
        quickSelect(counts, from, partitionIdx - 1, wantedIdx)
    }
}


fun main() {
    quickSelectSolution()
}
