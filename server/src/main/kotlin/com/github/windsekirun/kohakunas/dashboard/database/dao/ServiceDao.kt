package com.github.windsekirun.kohakunas.dashboard.database.dao

import com.github.windsekirun.kohakunas.dashboard.model.entity.Role
import com.github.windsekirun.kohakunas.dashboard.model.entity.Service
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select

object Services : Table(), ServiceDao {
    val id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", 128)
    val desc = text("desc")
    val connectUrl = varchar("connectUrl", 128)
    val role = enumeration("role", Role::class)
    val iconUrl = text("iconUrl")
    val category = text("category")
    val intranetService = bool("intranetService")

    override fun getServicesList(user: User) = select {
        when (user.role) {
            Role.User -> (role eq Role.User)
            Role.Lint -> (role inList listOf(Role.User, Role.Lint))
            Role.Admin -> (role inList listOf(Role.User, Role.Lint, Role.Admin))
        }
    }.mapNotNull {
        it.mapRowToService()
    }

    override fun getService(serviceId: Int) = select {
        id eq serviceId
    }.firstNotNullOf { it.mapRowToService() }
}

fun ResultRow.mapRowToService() =
    Service(
        id = this[Services.id],
        title = this[Services.title],
        desc = this[Services.desc],
        connectUrl = this[Services.connectUrl],
        role = this[Services.role],
        icon = this[Services.iconUrl].trim(),
        category = this[Services.category],
        intranetService = this[Services.intranetService]
    )

interface ServiceDao {
    fun getServicesList(user: User): List<Service>

    fun getService(serviceId: Int): Service
}

