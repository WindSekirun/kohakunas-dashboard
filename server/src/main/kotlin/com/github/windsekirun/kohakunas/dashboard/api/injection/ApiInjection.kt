package com.github.windsekirun.kohakunas.dashboard.api.injection

import com.github.windsekirun.kohakunas.dashboard.api.service.ServiceApi
import com.github.windsekirun.kohakunas.dashboard.api.service.ServiceApiImpl
import com.github.windsekirun.kohakunas.dashboard.api.user.UserApi
import com.github.windsekirun.kohakunas.dashboard.api.user.UserApiImpl
import org.koin.dsl.module

object ApiInjection {
    val koinBeans = module {
        single<UserApi> { UserApiImpl }
        single<ServiceApi> { ServiceApiImpl }
    }
}