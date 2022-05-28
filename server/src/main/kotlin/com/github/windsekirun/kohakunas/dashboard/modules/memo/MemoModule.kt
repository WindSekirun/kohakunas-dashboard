package com.github.windsekirun.kohakunas.dashboard.modules.memo

import com.github.windsekirun.kohakunas.dashboard.model.dto.MemoDTO
import com.github.windsekirun.kohakunas.dashboard.user
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.memoModule() {
    val memoController by inject<MemoController>()

    get("api/memo/{id}") {
        val id = requireNotNull(call.parameters["id"])
        require(id.isNotEmpty())
        call.respond(memoController.getMemoOfService(call.user, id.toInt()))
    }

    post("api/memo/{id}") {
        val id = requireNotNull(call.parameters["id"])
        require(id.isNotEmpty())
        val body = call.receive<MemoDTO.UpdateMemo>()
        call.respond(memoController.updateMemoOfService(call.user, id.toInt(), body))
    }
}