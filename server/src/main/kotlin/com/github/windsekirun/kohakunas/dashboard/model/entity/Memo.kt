package com.github.windsekirun.kohakunas.dashboard.model.entity

data class Memo(
    val id: Int,
    val serviceId: Int,
    val userId: Int,
    val memo: String
)