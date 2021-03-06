package com.github.windsekirun.kohakunas.dashboard.modules.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.github.windsekirun.kohakunas.dashboard.config.Config
import com.github.windsekirun.kohakunas.dashboard.model.dto.LoginDTO
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import java.util.*

object JwtConfig : TokenProvider {

    private const val issuer = "KokakuNAS"
    private const val validityInMs: Long = 3600000L * 24L // 24h
    private const val refrehsValidityInMs: Long = 3600000L * 24L * 30L // 30 days

    lateinit var verifier: JWTVerifier
    lateinit var algorithm: Algorithm

    fun initialize(config: Config) {
        algorithm = Algorithm.HMAC512(config.keySecret)
        verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build()
    }

    override fun verifyToken(token: String): Int? {
        return verifier.verify(token).claims["id"]?.asInt()
    }

    /**
     * Produce token and refresh token for this combination of User and Account
     */
    override fun createTokens(user: User): LoginDTO.CredentialsResponse {
        return LoginDTO.CredentialsResponse(
            user.userName,
            createToken(user, getTokenExpiration()),
            createToken(user, getTokenExpiration(refrehsValidityInMs))
        )
    }

    private fun createToken(user: User, expiration: Date) = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.id)
        .withClaim("userName", user.userName)
        .withClaim("role", user.role.desc)
        .withExpiresAt(expiration)
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getTokenExpiration(validity: Long = validityInMs) = Date(System.currentTimeMillis() + validity)
}

interface TokenProvider {
    fun createTokens(user: User): LoginDTO.CredentialsResponse
    fun verifyToken(token: String): Int?
}