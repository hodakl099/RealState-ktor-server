package com.example.plugins.routes.commercial.get

import com.example.dao.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllCommercialRoute() {
    get("properties") {
        val properties = dao.getAllCommercialProperties()
        if (properties.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, properties)
        } else {
            call.respond(HttpStatusCode.NotFound, "No properties found.")
        }
    }
}