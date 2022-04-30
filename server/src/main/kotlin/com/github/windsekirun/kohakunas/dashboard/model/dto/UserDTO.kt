package com.github.windsekirun.kohakunas.dashboard.model.dto

import com.github.windsekirun.kohakunas.dashboard.model.entity.User

class UserDTO {
    data class CreateUser(val userName: String, val password: String)

    data class Me(val userName: String) {
        companion object {
            fun fromUser(user: User) = Me(user.userName)
        }
    }
}