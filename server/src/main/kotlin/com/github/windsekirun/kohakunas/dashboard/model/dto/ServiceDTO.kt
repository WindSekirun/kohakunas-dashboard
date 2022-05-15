package com.github.windsekirun.kohakunas.dashboard.model.dto

import com.github.windsekirun.kohakunas.dashboard.model.entity.Service

class ServiceDTO {

    data class Category(val name: String, val services: List<Service>)
}