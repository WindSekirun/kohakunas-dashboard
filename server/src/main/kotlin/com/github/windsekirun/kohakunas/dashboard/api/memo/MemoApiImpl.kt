package com.github.windsekirun.kohakunas.dashboard.api.memo

import com.github.windsekirun.kohakunas.dashboard.database.dao.MemoDao
import com.github.windsekirun.kohakunas.dashboard.model.entity.Service
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import org.koin.core.KoinComponent
import org.koin.core.inject

object MemoApiImpl: MemoApi, KoinComponent {
    private val memoDao by inject<MemoDao>()

    override fun getMemoOfService(user: User, service: Service, password: String): String {
        val memoList = memoDao.getMemoOfService(user, service)
        if (memoList.isEmpty()) {
            return ""
        }
        // TODO: decrypt memo with password

        return ""
    }

    override fun updateMemoOfService(user: User, service: Service, password: String, memo: String) {
        TODO("Not yet implemented")
    }
}