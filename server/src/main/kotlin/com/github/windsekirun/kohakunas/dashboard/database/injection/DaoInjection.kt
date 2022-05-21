package com.github.windsekirun.kohakunas.dashboard.database.injection

import com.github.windsekirun.kohakunas.dashboard.database.dao.*
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<UserDao> { Users }
        single<ServiceDao> { Services }
        single<MemoDao> { Memos }
    }
}