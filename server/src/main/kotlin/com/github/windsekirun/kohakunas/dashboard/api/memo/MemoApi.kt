package com.github.windsekirun.kohakunas.dashboard.api.memo

import com.github.windsekirun.kohakunas.dashboard.model.entity.Service
import com.github.windsekirun.kohakunas.dashboard.model.entity.User

interface MemoApi {
    fun getMemoOfService(user: User, service: Service): String

    fun updateMemoOfService(user: User, service: Service, memo: String)
}