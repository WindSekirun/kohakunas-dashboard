package com.github.windsekirun.kohakunas.dashboard.modules.register

import com.github.windsekirun.kohakunas.dashboard.model.dto.LoginDTO
import com.github.windsekirun.kohakunas.dashboard.model.dto.UserDTO
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.registerModule() {

    val unauthenticatedController by inject<RegistrationController>()

    post("api/user") {
        val postUser = call.receive<UserDTO.CreateUser>()
        val user = unauthenticatedController.createUser(postUser)
        call.respond(user)
    }

    post("api/authenticate") {
        val credentials = call.receive<LoginDTO.LoginCredentials>()
        val loginTokenResponse = unauthenticatedController.authenticate(credentials)
        call.respond(loginTokenResponse)
    }

    post("api/token") {
        val credentials = call.receive<LoginDTO.RefreshBody>()
        val credentialsResponse = unauthenticatedController.refreshToken(credentials)
        call.respond(credentialsResponse)
    }
}