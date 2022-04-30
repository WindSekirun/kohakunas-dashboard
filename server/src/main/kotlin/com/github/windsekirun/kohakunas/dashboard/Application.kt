package com.github.windsekirun.kohakunas.dashboard

import com.github.windsekirun.kohakunas.dashboard.plugins.configureHTTP
import com.github.windsekirun.kohakunas.dashboard.plugins.configureRouting
import com.github.windsekirun.kohakunas.dashboard.plugins.configureSecurity
import com.github.windsekirun.kohakunas.dashboard.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    configureRouting()
    configureSerialization()
    configureHTTP()
    configureSecurity()
}