package com.github.windsekirun.kohakunas.dashboard.model.entity

data class Service(
    val id: Int,
    val title: String,
    val desc: String,
    val connectUrl: String,
    val role: Role
)