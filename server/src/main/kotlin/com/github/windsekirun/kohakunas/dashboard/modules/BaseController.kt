package com.github.windsekirun.kohakunas.dashboard.modules

import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProviderContract
import com.github.windsekirun.kohakunas.dashboard.statuspages.GeneralException
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

abstract class BaseController : KoinComponent {

    private val dbProvider by inject<DatabaseProviderContract>()

    suspend fun <T> dbQuery(block: () -> T): T {
        return dbProvider.dbQuery(block)
    }

    suspend fun <T> dbQueryCatching(block: () -> T): T = try {
        dbProvider.dbQuery(block)
    } catch (e: Exception) {
        throw GeneralException(cause = e)
    }
}