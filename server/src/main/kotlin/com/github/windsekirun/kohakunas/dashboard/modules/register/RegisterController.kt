package com.github.windsekirun.kohakunas.dashboard.modules.register

import com.github.windsekirun.kohakunas.dashboard.api.user.UserApi
import com.github.windsekirun.kohakunas.dashboard.model.dto.LoginDTO
import com.github.windsekirun.kohakunas.dashboard.model.dto.UserDTO
import com.github.windsekirun.kohakunas.dashboard.modules.BaseController
import com.github.windsekirun.kohakunas.dashboard.modules.auth.TokenProvider
import com.github.windsekirun.kohakunas.dashboard.statuspages.AuthenticationException
import com.github.windsekirun.kohakunas.dashboard.statuspages.GeneralException
import com.github.windsekirun.kohakunas.dashboard.statuspages.InvalidUserException
import com.github.windsekirun.kohakunas.dashboard.util.PasswordManagerContract
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegistrationControllerImpl : BaseController(), RegistrationController, KoinComponent {

    private val userApi by inject<UserApi>()
    private val passwordManager by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend fun createUser(postUser: UserDTO.CreateUser): UserDTO.Me {
        val user = dbQuery {
            userApi.getUserByName(postUser.userName)?.let {
                throw InvalidUserException("User is already taken")
            }
            userApi.createUser(postUser) ?: throw GeneralException("Internal server error")
        }
        return UserDTO.Me.fromUser(user)
    }

    override suspend fun authenticate(credentials: LoginDTO.LoginCredentials) = dbQuery {
        userApi.getUserByName(credentials.userName)?.let { user ->
            if (passwordManager.validatePassword(credentials.password, user.password)) {
                val credentialsResponse = tokenProvider.createTokens(user)
                LoginDTO.LoginTokenResponse(credentialsResponse)
            } else {
                throw AuthenticationException("Wrong credentials")
            }
        } ?: throw AuthenticationException("Wrong credentials")
    }

    override suspend fun refreshToken(credentials: LoginDTO.RefreshBody) = dbQuery {
        tokenProvider.verifyToken(credentials.refreshToken)?.let {
            userApi.getUserById(it)?.let { user ->
                val credentialsResponse = tokenProvider.createTokens(user)
                LoginDTO.LoginTokenResponse(credentialsResponse)
            } ?: throw AuthenticationException("Wrong credentials")
        } ?: throw AuthenticationException("Wrong credentials")
    }
}

interface RegistrationController {
    suspend fun createUser(postUser: UserDTO.CreateUser): UserDTO.Me
    suspend fun authenticate(credentials: LoginDTO.LoginCredentials): LoginDTO.LoginTokenResponse
    suspend fun refreshToken(credentials: LoginDTO.RefreshBody): LoginDTO.LoginTokenResponse
}