package com.github.windsekirun.kohakunas.dashboard.config

class Config(
    val port: Int,
    val databaseHost: String,
    val databasePort: String,
    val databaseUser: String,
    val databaseName: String,
    val databasePassword: String,
    val keySecret: String,
    val registerSecret: String
)