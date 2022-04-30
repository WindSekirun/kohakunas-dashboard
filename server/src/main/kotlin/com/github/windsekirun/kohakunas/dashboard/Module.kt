package com.github.windsekirun.kohakunas.dashboard

import com.auth0.jwt.interfaces.JWTVerifier
import com.github.windsekirun.kohakunas.dashboard.api.user.UserApi
import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProviderContract
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import com.github.windsekirun.kohakunas.dashboard.modules.auth.authenticationModule
import com.github.windsekirun.kohakunas.dashboard.modules.register.registerModule
import com.github.windsekirun.kohakunas.dashboard.modules.user.userModule
import com.github.windsekirun.kohakunas.dashboard.statuspages.authStatusPages
import com.github.windsekirun.kohakunas.dashboard.statuspages.generalStatusPages
import com.github.windsekirun.kohakunas.dashboard.statuspages.userStatusPages
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject
import org.slf4j.event.Level

fun Application.module() {
    val userApi by inject<UserApi>()
    val databaseProvider by inject<DatabaseProviderContract>()
    val jwtVerifier by inject<JWTVerifier>()
    //Init database here
    databaseProvider.init()

    install(CallLogging) {
        level = Level.DEBUG
    }
    install(ContentNegotiation) { gson { } }
    install(StatusPages) {
        generalStatusPages()
        userStatusPages()
        authStatusPages()

        exception<UnknownError> {
            call.respondText(
                "Internal server error",
                ContentType.Text.Plain,
                status = HttpStatusCode.InternalServerError
            )
        }
        exception<IllegalArgumentException> {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    install(Authentication) {
        authenticationModule(userApi, databaseProvider, jwtVerifier)
    }

    install(Routing) {
        static("/static") {
            resources("static")
        }
        registerModule()
        authenticate("jwt") {
            userModule()
        }
    }
}

val ApplicationCall.user get() = requireNotNull(authentication.principal<User>())

suspend fun PipelineContext<Unit, ApplicationCall>.sendOk() {
    call.respond(HttpStatusCode.OK)
}