package com.github.windsekirun.kohakunas.dashboard.api.user

import com.github.windsekirun.kohakunas.dashboard.model.dto.UserDTO
import com.github.windsekirun.kohakunas.dashboard.model.entity.User

interface UserApi {
    fun getUserById(id: Int): User?
    fun getUserByName(username: String): User?
    fun removeUser(userId: Int): Boolean
    fun createUser(postUser: UserDTO.CreateUser): User?
}