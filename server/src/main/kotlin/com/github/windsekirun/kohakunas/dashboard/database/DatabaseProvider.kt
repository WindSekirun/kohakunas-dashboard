package com.github.windsekirun.kohakunas.dashboard.database

import com.github.windsekirun.kohakunas.dashboard.config.Config
import com.github.windsekirun.kohakunas.dashboard.database.dao.Memos
import com.github.windsekirun.kohakunas.dashboard.database.dao.Services
import com.github.windsekirun.kohakunas.dashboard.database.dao.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class DatabaseProvider : DatabaseProviderContract, KoinComponent {

    private val config by inject<Config>()
    private val dispatcher: CoroutineContext

    init {
        dispatcher = Executors.newFixedThreadPool(5).asCoroutineDispatcher()
    }

    override fun init() {
        Database.connect(hikari(config))
        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.create(Services)
            SchemaUtils.create(Memos)
        }
    }

    private fun hikari(config: Config): HikariDataSource {
        HikariConfig().run {
            jdbcUrl = "jdbc:mysql://${config.databaseHost}:${config.databasePort}/${config.databaseName}"
            username = config.databaseUser
            password = config.databasePassword
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
            return HikariDataSource(this)
        }
    }

    override suspend fun <T> dbQuery(block: () -> T): T = withContext(dispatcher) {
        transaction { block() }
    }
}

interface DatabaseProviderContract {
    fun init()
    suspend fun <T> dbQuery(block: () -> T): T
}