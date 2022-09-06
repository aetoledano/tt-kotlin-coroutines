import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.PrintWriter
import kotlin.random.Random
import kotlin.system.measureTimeMillis

const val FILENAME = "matrix.sample"
const val ROWS = 100
const val COLS = 5_000_000

fun main(args: Array<String>) = runBlocking {
    generateDataset()
    val matrix: Array<IntArray>
    println(
        "in ${measureTimeMillis { matrix = readDataset() }} ms"
    )
    
    println()
    
    println("<<< Coroutines >>>")
    testCoroutines(matrix)
    testCoroutines(matrix)
    testCoroutines(matrix)
    testCoroutines(matrix)
    testCoroutines(matrix)
    testCoroutines(matrix)
    
    println()
    
    println("<<< No Coroutines >>>")
    test(matrix)
    test(matrix)
    test(matrix)
    test(matrix)
    test(matrix)
    test(matrix)
    
    return@runBlocking
}

fun test(matrix: Array<IntArray>) {
    var count: Long
    var time: Long
    
    time = measureTimeMillis {
        count = find(50, matrix)
    }
    println("Target found $count times in $time ms")
}

suspend fun testCoroutines(matrix: Array<IntArray>) {
    var count: Long
    var time: Long
    
    time = measureTimeMillis {
        count = findCoroutines(50, matrix)
    }
    println("Target found $count times in $time ms")
}

fun find(target: Int, matrix: Array<IntArray>): Long {
    var count = 0L
    
    for (row in matrix)
        for (e in row)
            if (target == e) count++
    
    return count
}

suspend fun findCoroutines(target: Int, matrix: Array<IntArray>): Long {
    return matrix.map { row ->
        CoroutineScope(Dispatchers.Default).async {
            var count = 0L
            for (e in row) if (target == e) count++
            count
        }
    }.awaitAll().sum()
}

fun readDataset(): Array<IntArray> {
    val file = File(FILENAME)
    val buff = BufferedReader(FileReader(file))
    
    var matrix = Array(ROWS) { IntArray(COLS) }
    
    var i = -1
    while (i++ < ROWS) {
        val line = buff.readLine()
        if (line.isNullOrEmpty() || line.isBlank()) continue
        
        parseLine(line, matrix[i])
    }
    
    buff.close()
    print("Dataset read ! ")
    return matrix
}

fun parseLine(line: String, store: IntArray) {
        var buff = ""
        var idx = 0
        for (c in line) {
            if (c != ' ') {
                buff += c
            } else {
                store[idx] = buff.toInt()
                idx++
                buff = ""
            }
        }
    }

fun printDataset(matrix: Array<IntArray>) {
    for (row in matrix) {
        for (e in row)
            print("$e ")
        println()
    }
}

fun generateDataset() {
    val file = File(FILENAME)
    
    val w = PrintWriter(file)
    repeat(ROWS) {
        repeat(COLS) {
            w.print("${Random.nextInt(0, 100)} ")
        }
        w.println()
    }
    w.flush()
    w.close()
    println("Dataset generated !")
}
