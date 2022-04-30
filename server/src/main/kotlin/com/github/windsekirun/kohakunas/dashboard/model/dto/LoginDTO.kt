package com.github.windsekirun.kohakunas.dashboard.model.dto

class LoginDTO {
    data class LoginCredentials(val username: String, val password: String)

    data class LoginTokenResponse(val credentials: CredentialsResponse)

    data class CredentialsResponse(val accessToken: String, val refreshToken: String)

    data class RefreshBody(val username: String, val refreshToken: String)
}