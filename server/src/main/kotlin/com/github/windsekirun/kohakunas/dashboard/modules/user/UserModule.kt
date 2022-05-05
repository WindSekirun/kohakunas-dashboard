package com.github.windsekirun.kohakunas.dashboard.modules.user

import com.github.windsekirun.kohakunas.dashboard.model.dto.UserDTO
import com.github.windsekirun.kohakunas.dashboard.sendOk
import com.github.windsekirun.kohakunas.dashboard.user
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.userModule() {
    val controller by inject<UserController>()

    get("api/me") {
        call.respond(UserDTO.Me.fromUser(call.user))
    }

    delete("api/user") {
        controller.removeUser(call.user.id)
        sendOk()
    }
}