package com.github.windsekirun.kohakunas.dashboard.modules.admin

import com.github.windsekirun.kohakunas.dashboard.model.dto.AdminDTO
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.adminModule() {
    val controller by inject<AdminController>()

    post("/api/user/role") {
        val request = call.receive<AdminDTO.ChangeRoleRequest>()
        controller.changeUserRole(request.userId, request.role)
    }
}