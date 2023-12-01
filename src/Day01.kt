val mappings = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

fun notWorking(line: String): String {
    var startIndex = 0
    var readAhead = 1
    var replaced = line
    while (startIndex <= replaced.length) {
        val end = startIndex + readAhead
        val ssRange = startIndex ..< if(end >= replaced.length) replaced.length else end

        val part = replaced.substring(ssRange)
        val found = mappings[part]

        if (found != null) {
            replaced = replaced.replaceRange(ssRange, found)
            startIndex = 0
            readAhead = 1
        } else {
            if (end < replaced.length) {
                readAhead += 1
            } else {
                startIndex += 1
                readAhead = 1
            }
        }
    }

    return replaced
}

fun getOccurrence(line: String, lastOccurrence: Boolean = false): Int {
    var targetIndex = Pair(if(lastOccurrence) Int.MIN_VALUE else Int.MAX_VALUE, "")
//    println("line: $line")
    for(v in mappings.keys + mappings.values) {
        val index = if(lastOccurrence) line.lastIndexOf(v) else line.indexOf(v)

        val condition = if(lastOccurrence) index > targetIndex.first else index < targetIndex.first
        if(index >= 0 && condition) {
            targetIndex = Pair(index, v)
        }
    }

//    println(targetIndex)
    return targetIndex.second.toIntOrNull() ?: mappings.getValue(targetIndex.second).toInt()
}

fun List<String>.accumulate(): Int =
    this.map { line ->
        val digits = line.filter { c -> c.isDigit() }
        digits.first() + digits.last().toString()
    }.fold(0) { acc, digit ->
        acc + digit.toInt()
    }

fun main() {

    fun part1(input: List<String>): Int {
        return input.accumulate()
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val first = getOccurrence(line)
            val last = getOccurrence(line, true)
            println("$first$last")
            first.toString() + last.toString()
        }.accumulate()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day_01/day_01_2_test")
    part2(testInput).println()

    val input = readInput("day_01/day_01")
//    part1(input).println()
    part2(input).println()
}
