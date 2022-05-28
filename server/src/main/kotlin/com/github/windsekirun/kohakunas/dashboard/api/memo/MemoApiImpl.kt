package com.github.windsekirun.kohakunas.dashboard.api.memo

import com.github.windsekirun.kohakunas.dashboard.database.dao.MemoDao
import com.github.windsekirun.kohakunas.dashboard.model.entity.Service
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import com.github.windsekirun.kohakunas.dashboard.util.Encrypter
import org.koin.core.KoinComponent
import org.koin.core.inject

object MemoApiImpl : MemoApi, KoinComponent {
    private val memoDao by inject<MemoDao>()

    override fun getMemoOfService(user: User, service: Service): String {
        val memoList = memoDao.getMemoOfService(user, service)
        if (memoList.isEmpty()) {
            return ""
        }
        return Encrypter.decrypt(memoList.first().memo)
    }

    override fun updateMemoOfService(user: User, service: Service, memo: String) {
        val encrypted = Encrypter.encrypt(memo)
        return memoDao.updateMemoOfService(user, service, encrypted)
    }
}