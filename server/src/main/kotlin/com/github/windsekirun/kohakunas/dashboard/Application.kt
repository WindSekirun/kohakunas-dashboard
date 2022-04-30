package com.github.windsekirun.kohakunas.dashboard

import com.github.windsekirun.kohakunas.dashboard.plugins.configureHTTP
import com.github.windsekirun.kohakunas.dashboard.plugins.configureRouting
import com.github.windsekirun.kohakunas.dashboard.plugins.configureSecurity
import com.github.windsekirun.kohakunas.dashboard.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureHTTP()
        configureSecurity()
    }.start(wait = true)
}
