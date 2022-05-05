package com.github.windsekirun.kohakunas.dashboard.model.dto

import com.github.windsekirun.kohakunas.dashboard.model.entity.Role

class AdminDTO {
    data class ChangeRoleRequest(val userId: Int, val role: Role)
}