package com.github.windsekirun.kohakunas.dashboard.model.dto

class LoginDTO {
    data class LoginCredentials(val userName: String, val password: String)

    data class LoginTokenResponse(val credentials: CredentialsResponse)

    data class CredentialsResponse(val userName: String, val accessToken: String, val refreshToken: String)

    data class RefreshBody(val userName: String, val refreshToken: String)
}