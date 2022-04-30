package com.github.windsekirun.kohakunas.dashboard.modules.service

import com.github.windsekirun.kohakunas.dashboard.api.service.ServiceApi
import com.github.windsekirun.kohakunas.dashboard.model.entity.Service
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import com.github.windsekirun.kohakunas.dashboard.modules.BaseController
import com.github.windsekirun.kohakunas.dashboard.statuspages.GeneralException
import org.koin.core.KoinComponent
import org.koin.core.inject

class ServiceControllerImpl: BaseController(), ServiceController, KoinComponent {

    private val serviceApi by inject<ServiceApi>()

    override suspend fun getServicesList(user: User) = dbQuery {
        try {
            serviceApi.getServiceList(user)
        } catch (e: Exception) {
            throw GeneralException("Can't find any services match by user")
        }
    }
}

interface ServiceController {
    suspend fun getServicesList(user: User): List<Service>
}