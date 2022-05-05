package com.github.windsekirun.kohakunas.dashboard.api.user

import com.github.windsekirun.kohakunas.dashboard.config.Config
import com.github.windsekirun.kohakunas.dashboard.statuspages.InvalidUserException
import com.github.windsekirun.kohakunas.dashboard.database.dao.UserDao
import com.github.windsekirun.kohakunas.dashboard.model.dto.UserDTO
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import com.github.windsekirun.kohakunas.dashboard.util.PasswordManagerContract
import org.koin.core.KoinComponent
import org.koin.core.inject

object UserApiImpl : UserApi, KoinComponent {

    private val usersDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManagerContract>()
    private val config by inject<Config>()

    override fun getUserById(id: Int) = usersDao.getUserById(id)

    override fun createUser(postUser: UserDTO.CreateUser): User {
        if (postUser.registerSecret != config.registerSecret) {
            throw InvalidUserException("Doesn't match REGISTER_SECRET")
        }

        val encryptedUser = postUser.copy(password = passwordEncryption.encryptPassword(postUser.password))
        val key: Int? = usersDao.insertUser(encryptedUser)
        return key?.let {
            usersDao.getUserById(it)
        } ?: throw InvalidUserException("Error while creating user")
    }

    override fun removeUser(userId: Int) = usersDao.deleteUser(userId)

    override fun getUserByName(username: String) = usersDao.getUserByName(username)
}
