package com.github.windsekirun.kohakunas.dashboard.modules.service

import com.github.windsekirun.kohakunas.dashboard.user
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.serviceModule() {
    val controller by inject<ServiceController>()

    get("api/services") {
        call.respond(controller.getServicesList(call.user))
    }
}