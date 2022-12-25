package page.lasta.study.elasticsearch2.converter.zipcode

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Path to csv file is required.")
        exitProcess(1)
    }

    val csvFile = args[0]
    val records = csvReader().open(csvFile) {

    }
}
