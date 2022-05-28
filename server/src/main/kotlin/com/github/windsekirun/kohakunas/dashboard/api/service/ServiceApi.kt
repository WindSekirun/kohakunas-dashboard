package com.github.windsekirun.kohakunas.dashboard.api.service

import com.github.windsekirun.kohakunas.dashboard.model.entity.Service
import com.github.windsekirun.kohakunas.dashboard.model.entity.User

interface ServiceApi {
    fun getServiceList(user: User): List<Service>

    fun getService(serviceId: Int): Service
}