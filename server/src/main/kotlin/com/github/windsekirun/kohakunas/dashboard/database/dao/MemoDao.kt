package com.github.windsekirun.kohakunas.dashboard.database.dao

import com.github.windsekirun.kohakunas.dashboard.model.entity.Memo
import com.github.windsekirun.kohakunas.dashboard.model.entity.Service
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import org.jetbrains.exposed.sql.*

object Memos : Table(), MemoDao {
    val id = integer("id").autoIncrement().primaryKey()
    val serviceId = integer("serviceId")
    val userId = integer("userId")
    val memo = text("memo")

    override fun getMemoOfService(user: User, service: Service) = select {
        ((userId) eq user.id) and ((serviceId) eq service.id)
    }.mapNotNull {
        it.mapRowToMemo()
    }

    override fun updateMemoOfService(user: User, service: Service, newMemo: String) {
        if (getMemoOfService(user, service).isEmpty()) {
            insert {
                it[serviceId] = service.id
                it[userId] = user.id
                it[memo] = newMemo
            }
        } else {
            update(where = {
                ((userId) eq user.id) and ((serviceId) eq service.id)
            }, body = {
                it[memo] = newMemo
            })
        }
    }
}

fun ResultRow.mapRowToMemo() = Memo(
    id = this[Memos.id],
    serviceId = this[Memos.serviceId],
    userId = this[Memos.userId],
    memo = this[Memos.memo]
)

interface MemoDao {
    fun getMemoOfService(user: User, service: Service): List<Memo>

    fun updateMemoOfService(user: User, service: Service, newMemo: String)
}