package com.github.windsekirun.kohakunas.dashboard.modules.user

import com.github.windsekirun.kohakunas.dashboard.api.user.UserApi
import com.github.windsekirun.kohakunas.dashboard.modules.BaseController
import com.github.windsekirun.kohakunas.dashboard.statuspages.GeneralException
import org.koin.core.KoinComponent
import org.koin.core.inject

class UserControllerImpl : BaseController(), UserController, KoinComponent {

    private val userApi by inject<UserApi>()

    override suspend fun removeUser(userId: Int) {
        dbQueryCatching {
            userApi.removeUser(userId)
        }
    }

}

interface UserController {
    suspend fun removeUser(userId: Int)
}