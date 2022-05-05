package com.github.windsekirun.kohakunas.dashboard.modules.admin

import com.github.windsekirun.kohakunas.dashboard.model.dto.UserDTO
import com.github.windsekirun.kohakunas.dashboard.user
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.adminModule() {
    get("api/admin") {
        call.respond(UserDTO.Me.fromUser(call.user))
    }
}