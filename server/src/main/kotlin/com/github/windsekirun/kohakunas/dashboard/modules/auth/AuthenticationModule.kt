package com.github.windsekirun.kohakunas.dashboard.modules.auth

import com.auth0.jwt.interfaces.JWTVerifier
import com.github.windsekirun.kohakunas.dashboard.api.user.UserApi
import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProviderContract
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Authentication.Configuration.authenticationModule(
    userApi: UserApi,
    databaseProvider: DatabaseProviderContract,
    tokenVerifier: JWTVerifier
) {
    /**
     * Setup the JWT authentication to be used in [Routing].
     * If the token is valid, the corresponding [User] is fetched from the database.
     * The [User] can then be accessed in each [ApplicationCall].
     */
    jwt("jwt") {
        verifier(tokenVerifier)
        realm = "kohaku.moe"
        validate {
            it.payload.getClaim("id").asInt()?.let { userId ->
                // do database query to find Principal subclass
                databaseProvider.dbQuery {
                    userApi.getUserById(userId)
                }
            }
        }
    }
}