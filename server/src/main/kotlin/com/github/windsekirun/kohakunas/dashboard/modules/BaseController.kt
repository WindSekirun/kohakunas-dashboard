package com.github.windsekirun.kohakunas.dashboard.modules

import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProviderContract
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseController : KoinComponent {

    private val dbProvider by inject<DatabaseProviderContract>()

    suspend fun <T> dbQuery(block: () -> T): T {
        return dbProvider.dbQuery(block)
    }
}