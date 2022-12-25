package page.lasta.study.elasticsearch2.converter.zipcode

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import page.lasta.study.elasticsearch2.converter.indexer.ElasticsearchClient
import page.lasta.study.elasticsearch2.converter.indexer.ElasticsearchClientImpl
import page.lasta.study.elasticsearch2.converter.indexer.IndexAction
import page.lasta.study.elasticsearch2.converter.indexer.IndexActionAndMetadata
import java.nio.file.Paths
import kotlin.system.exitProcess

@OptIn(ExperimentalSerializationApi::class)
fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Path to csv file is required.")
        exitProcess(1)
    }
    val csvFile = Paths.get(args[0]).toFile()
    val csv = Csv { hasHeaderRecord = false }
    val records: List<ZipCode> = csv.decodeFromString(ListSerializer(ZipCode.serializer()), csvFile.readText())

    val bulkData: Sequence<Pair<IndexAction, ZipcodeDocument>> = sequence {
        records.forEachIndexed { index, record ->
            val action = IndexAction(
                IndexActionAndMetadata(
                    index = "zipcode",
                    id = index.toString()
                )
            )

            val document = record.toDocument()
            yield(action to document)
        }
    }

    val elasticsearchClient: ElasticsearchClient = ElasticsearchClientImpl()
    val deleteResponse = runBlocking {
        elasticsearchClient.deleteAll("zipcode")
    }

    if (deleteResponse.status != HttpStatusCode.OK) {
        throw IllegalStateException(deleteResponse.toString())
    }
    println(deleteResponse)

    val response = runBlocking {
        elasticsearchClient.bulkIndex(bulkData, ZipcodeDocument.serializer())
    }
    println(response)
}
