package page.lasta.study.elasticsearch2

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import elasticsearch2.study.lasta.page.plugins.*
import page.lasta.study.elasticsearch2.plugins.configureHTTP
import page.lasta.study.elasticsearch2.plugins.configureMonitoring
import page.lasta.study.elasticsearch2.plugins.configureRouting
import page.lasta.study.elasticsearch2.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
