package com.github.windsekirun.kohakunas.dashboard.modules.auth

import com.auth0.jwt.interfaces.JWTVerifier
import com.github.windsekirun.kohakunas.dashboard.api.user.UserApi
import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProvider
import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProviderContract
import com.github.windsekirun.kohakunas.dashboard.model.entity.Role
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Authentication.Configuration.authenticationModule(
    userApi: UserApi,
    databaseProvider: DatabaseProviderContract,
    tokenVerifier: JWTVerifier
) {
    jwt("plain") {
        verifier(tokenVerifier)
        realm = REALM
        validate {
            it.queryUser(databaseProvider, userApi)
        }
    }

    jwt("admin") {
        verifier(tokenVerifier)
        realm = REALM
        validate {
            it.queryUser(databaseProvider, userApi) { user ->
                require(user?.role == Role.Admin) { "Doesn't match ROLE required" }
            }
        }
    }
}

suspend fun JWTCredential.queryUser(
    databaseProvider: DatabaseProviderContract,
    userApi: UserApi,
    block: (User?) -> Unit = {}
) = this.payload.getClaim("id").asInt()?.let { userId ->
    val user = databaseProvider.dbQuery {
        userApi.getUserById(userId)
    }

    block(user)
    user
}

const val REALM = "kohaku.moe"