package com.github.windsekirun.kohakunas.dashboard.model.entity

import io.ktor.auth.*

data class User(
    val id: Int,
    val userName: String,
    val password: String,
    val role: Role
) : Principal