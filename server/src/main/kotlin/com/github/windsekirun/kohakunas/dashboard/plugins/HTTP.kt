package com.github.windsekirun.kohakunas.dashboard.plugins

import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureHTTP() {
    install(CORS) {
        this.allowMethod(HttpMethod.Options)
        this.allowMethod(HttpMethod.Put)
        this.allowMethod(HttpMethod.Delete)
        this.allowMethod(HttpMethod.Patch)
        this.allowHeader(HttpHeaders.Authorization)
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

}
