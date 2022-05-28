package com.github.windsekirun.kohakunas.dashboard.modules.injection

import com.github.windsekirun.kohakunas.dashboard.modules.admin.AdminController
import com.github.windsekirun.kohakunas.dashboard.modules.admin.AdminControllerImpl
import com.github.windsekirun.kohakunas.dashboard.modules.memo.MemoController
import com.github.windsekirun.kohakunas.dashboard.modules.memo.MemoControllerImpl
import com.github.windsekirun.kohakunas.dashboard.modules.register.RegistrationController
import com.github.windsekirun.kohakunas.dashboard.modules.register.RegistrationControllerImpl
import com.github.windsekirun.kohakunas.dashboard.modules.service.ServiceController
import com.github.windsekirun.kohakunas.dashboard.modules.service.ServiceControllerImpl
import com.github.windsekirun.kohakunas.dashboard.modules.user.UserController
import com.github.windsekirun.kohakunas.dashboard.modules.user.UserControllerImpl
import org.koin.dsl.module

object ModulesInjection {
    val koinBeans = module {
        single<RegistrationController> { RegistrationControllerImpl() }
        single<UserController> { UserControllerImpl() }
        single<ServiceController> { ServiceControllerImpl() }
        single<AdminController> { AdminControllerImpl() }
        single<MemoController> { MemoControllerImpl() }
    }
}