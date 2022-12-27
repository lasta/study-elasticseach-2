# Study Elasticsaerch with Ktor
## Environment
* Elasticsearch 8.5
  * [Version 8.6.0 of Elasticsearch has not yet been released, so no Docker image is currently available for this version.](https://www.elastic.co/guide/en/elasticsearch/reference/8.6/docker.html)

## Index zipcode data
1. Run docker containers ([middlewares/README.md](middlewares/README.md))
2. Get and convert data ([middlewares/elasticsearch/zipcode/README.md](middlewares/elasticsearch/zipcode/README.md))
3. Index data ([converter/zipcode/main.kt](src/main/kotlin/page/lasta/study/elasticsearch2/converter/zipcode/main.kt))
