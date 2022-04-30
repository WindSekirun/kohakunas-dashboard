package com.github.windsekirun.kohakunas.dashboard.model

import org.jetbrains.exposed.sql.Table

data class HostService(
    val id: Int,
    val title: String,
    val desc: String,
    val connectUrl: String,
    val role: Role
)

object HostServices : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val desc = text("desc")
    val connectUrl = varchar("connectUrl", 128)
    val role = enumeration("role", Role::class)

    override val primaryKey = PrimaryKey(id)
}

