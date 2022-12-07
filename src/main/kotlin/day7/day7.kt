package day7

import java.io.File


class Node(
    var parent: Node?,
    var children: MutableMap<String, Node>,
    var type: Type,
    var size: Int
)

enum class State {
    COMMAND, LS,
}

enum class Type {
    DIR, FILE
}

var minDiff = Int.MAX_VALUE
var deletedDirSize = 0


fun main() {
    val path = "src/main/kotlin/day7/input.txt"

    val root = Node(null, mutableMapOf(), Type.DIR, 0)

    val commands = File(path).readLines()
    parse(root, commands)

    val toRemove = mutableListOf<Int>()
    calculatesSizesToRemove(root, toRemove)

    val all = 70_000_000
    val want = 30_000_000
    val used = root.size
    val free = all - used
    val needToFree = want - free
    findClosestGeq(root, needToFree)

    println(minDiff)
    println("need to free $needToFree")
    println("will delete $deletedDirSize")
    println("diff: $minDiff")
}


fun findClosestGeq(node: Node, needToFree: Int) {
    if (node.type == Type.DIR) {
        if (node.size >= needToFree) {
            if (node.size - needToFree < minDiff) {
                minDiff = node.size - needToFree
                deletedDirSize = node.size
            }
        }

        for (n in node.children) {
            findClosestGeq(n.value, needToFree)
        }
    }
}

fun calculatesSizesToRemove(node: Node, toRemove: MutableList<Int>): Int {
    return if (node.type == Type.DIR) {
        for (n in node.children) {
            node.size += calculatesSizesToRemove(n.value, toRemove)
        }
        if (node.size <= 100000) {
            toRemove.add(node.size)
        }
        node.size
    } else {
        node.size
    }
}

fun parse(root: Node, output: List<String>) {
    var mode = State.COMMAND
    var node = root
    for (o in output) {
        if (o == "\$ ls") {
            mode = State.LS
            continue
        }

        if (o.startsWith("\$ cd")) {
            val dir = o.split(" ").last()
            mode = State.COMMAND
            if (dir == "..") {
                node = node.parent!!
                continue
            }

            if (dir == "/") {
                node = root
                continue
            }

            if (dir !in node.children) {
                val newDir = Node(node, mutableMapOf(), Type.DIR, 0)
                node.children[dir] = newDir
            }
            node = node.children[dir]!!
            continue
        }

        if (mode == State.LS) {
            if (o.startsWith("dir")) {
                val dir = o.split(" ").last()
                if (dir !in node.children) {
                    val newDir = Node(node, mutableMapOf(), Type.DIR, 0)
                    node.children[dir] = newDir
                }
            } else {
                val fileLine = o.split(" ")
                val size = fileLine[0].toInt()
                val name = fileLine[1]

                if (name !in node.children) {
                    val newFile = Node(node, mutableMapOf(), Type.FILE, size)
                    node.children[name] = newFile
                }
            }
        }
    }
}