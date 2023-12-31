package com.example.plugins.routes.agricultural.get

import com.example.dao.dao
import com.example.util.BasicApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Get Route.
 */
fun Route.getAgriculturalRoute() {
    get("property/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if(id != null) {
            val property = dao.getAgriculturalProperty(id)
            if (property != null) {
                call.respond(HttpStatusCode.OK, property)
            }else {
                call.respond(HttpStatusCode.NotFound, "No property found with the provided ID.")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid or missing ID.")
        }
    }
}