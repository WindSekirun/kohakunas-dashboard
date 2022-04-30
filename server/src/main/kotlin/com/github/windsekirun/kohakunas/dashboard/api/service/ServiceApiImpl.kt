package com.github.windsekirun.kohakunas.dashboard.api.service

import com.github.windsekirun.kohakunas.dashboard.database.dao.ServiceDao
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import org.koin.core.KoinComponent
import org.koin.core.inject

object ServiceApiImpl : ServiceApi, KoinComponent {

    private val serviceDao by inject<ServiceDao>()

    override fun getServiceList(user: User) = serviceDao.getServicesList(user)
}
