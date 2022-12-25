package page.lasta.study.elasticsearch2.converter.indexer

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndexAction(
    @SerialName("index")
    val index: IndexActionAndMetadata
)

@Serializable
data class IndexActionAndMetadata(
    @SerialName("_index")
    val index: String,
    @SerialName("_id")
    val id: String,
    @SerialName("require_alias")
    val requireAlias: Boolean = false,
    @SerialName("dynamic_templates")
    val dynamicTemplates: Map<String, String> = emptyMap()// FIXME: type
)
