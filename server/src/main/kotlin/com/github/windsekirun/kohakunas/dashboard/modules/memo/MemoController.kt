package com.github.windsekirun.kohakunas.dashboard.modules.memo

import com.github.windsekirun.kohakunas.dashboard.api.memo.MemoApi
import com.github.windsekirun.kohakunas.dashboard.api.service.ServiceApi
import com.github.windsekirun.kohakunas.dashboard.model.dto.MemoDTO
import com.github.windsekirun.kohakunas.dashboard.model.entity.User
import com.github.windsekirun.kohakunas.dashboard.modules.BaseController
import org.koin.core.KoinComponent
import org.koin.core.inject

class MemoControllerImpl : BaseController(), MemoController, KoinComponent {
    private val memoApi by inject<MemoApi>()
    private val serviceApi by inject<ServiceApi>()

    override suspend fun getMemoOfService(user: User, serviceId: Int) = dbQueryCatching {
        val service = serviceApi.getService(serviceId)
        memoApi.getMemoOfService(user, service)
    }

    override suspend fun updateMemoOfService(user: User, serviceId: Int, updateMemo: MemoDTO.UpdateMemo) =
        dbQueryCatching {
            val service = serviceApi.getService(serviceId)
            memoApi.updateMemoOfService(user, service, updateMemo.memo)
        }
}

interface MemoController {
    suspend fun getMemoOfService(user: User, serviceId: Int): String
    suspend fun updateMemoOfService(user: User, serviceId: Int, updateMemo: MemoDTO.UpdateMemo)
}