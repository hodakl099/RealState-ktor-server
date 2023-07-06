package com.example.plugins.routes.office.get

import com.example.dao.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllOfficeProperty() {
    get("properties") {
        val properties = dao.getAllIndustrialProperties()
        if (properties.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, properties)
        } else {
            call.respond(HttpStatusCode.NotFound, "No properties found.")
        }
    }
}