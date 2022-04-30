package com.github.windsekirun.kohakunas.dashboard.database.injection

import com.github.windsekirun.kohakunas.dashboard.database.dao.ServiceDao
import com.github.windsekirun.kohakunas.dashboard.database.dao.Services
import com.github.windsekirun.kohakunas.dashboard.database.dao.UserDao
import com.github.windsekirun.kohakunas.dashboard.database.dao.Users
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<UserDao> { Users }
        single<ServiceDao> { Services }
    }
}