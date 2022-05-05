package com.github.windsekirun.kohakunas.dashboard.modules.admin

import com.github.windsekirun.kohakunas.dashboard.api.user.UserApi
import com.github.windsekirun.kohakunas.dashboard.model.entity.Role
import com.github.windsekirun.kohakunas.dashboard.modules.BaseController
import org.koin.core.KoinComponent
import org.koin.core.inject

class AdminControllerImpl : BaseController(), AdminController, KoinComponent {
    private val userApi by inject<UserApi>()

    override suspend fun changeUserRole(user: Int, role: Role) = dbQueryCatching {
        userApi.changeUserRole(user, role)
    }
}

interface AdminController {
    suspend fun changeUserRole(user: Int, role: Role): Int
}