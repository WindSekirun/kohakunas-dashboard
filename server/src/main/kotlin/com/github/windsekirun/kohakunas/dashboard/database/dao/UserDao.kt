package com.github.windsekirun.kohakunas.dashboard.database.dao

import com.github.windsekirun.kohakunas.dashboard.model.dto.UserDTO
import com.github.windsekirun.kohakunas.dashboard.model.entity.Role
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import org.jetbrains.exposed.sql.*

@Suppress("MemberVisibilityCanBePrivate")
object Users : Table(), UserDao {
    val id = integer("id").autoIncrement().primaryKey()
    val userName = varchar("userName", 255)
    val creationTime = long("creationTime")
    val password = varchar("password", 255)
    val role = enumeration("role", Role::class)

    override fun getUserById(userId: Int) = select {
        (id eq userId)
    }.mapNotNull {
        it.mapRowToUser()
    }.singleOrNull()

    override fun insertUser(postUser: UserDTO.CreateUser) = (insert {
        it[userName] = postUser.userName
        it[password] = postUser.password
        it[creationTime] = System.currentTimeMillis()
        it[role] = Role.User // default - user
    })[id]

    override fun deleteUser(userId: Int) = deleteWhere { (id eq userId) } > 0

    override fun getUserByName(userNameValue: String) = select {
        (userName eq userNameValue)
    }.mapNotNull {
        it.mapRowToUser()
    }.singleOrNull()

    override fun changeUserRole(userId: Int, newRole: Role) = update(where = {
        (id eq userId)
    }, body = {
        it[role] = newRole
    })
}

fun ResultRow.mapRowToUser() =
    User(
        id = this[Users.id],
        userName = this[Users.userName],
        password = this[Users.password],
        role = this[Users.role]
    )

interface UserDao {
    fun getUserById(userId: Int): User?
    fun insertUser(postUser: UserDTO.CreateUser): Int?
    fun deleteUser(userId: Int): Boolean
    fun getUserByName(userNameValue: String): User?
    fun changeUserRole(userId: Int, newRole: Role): Int
}