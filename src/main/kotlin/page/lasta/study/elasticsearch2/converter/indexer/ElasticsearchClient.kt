package page.lasta.study.elasticsearch2.converter.indexer

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface ElasticsearchClient {

    val httpClientSupplier: () -> HttpClient
    val baseUrl: String

    suspend fun <T> bulkIndex(
        bulkData: Sequence<Pair<IndexAction, T>>,
        serializer: SerializationStrategy<T>
    ): HttpResponse

    suspend fun deleteAll(indexName: String): HttpResponse
}

internal class ElasticsearchClientImpl(
    override val httpClientSupplier: () -> HttpClient = { HttpClient(Java) },
    override val baseUrl: String = "http://localhost:9200"
) : ElasticsearchClient {
    override suspend fun <T> bulkIndex(
        bulkData: Sequence<Pair<IndexAction, T>>,
        serializer: SerializationStrategy<T>
    ): HttpResponse = httpClientSupplier().use { client ->
        client.put("$baseUrl/_bulk") {
            header("Content-Type", "application/x-ndjson")
            setBody(
                bulkData.joinToString("\n") { (action, document) ->
                    Json.encodeToString(action) + "\n" + Json.encodeToString(serializer, document)
                } + "\n"
            )
        }
    }

    override suspend fun deleteAll(indexName: String): HttpResponse = httpClientSupplier().use { client ->
        client.post("$baseUrl/$indexName/_delete_by_query") {
            contentType(ContentType.Application.Json)
            setBody(MATCH_ALL_QUERY)
        }
    }

    companion object {
        private const val MATCH_ALL_QUERY: String = """
            {
                "query": {
                    "match_all": {}
                }
            }
        """
    }
}
