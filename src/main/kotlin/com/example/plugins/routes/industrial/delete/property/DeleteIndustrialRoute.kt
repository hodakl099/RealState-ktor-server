package com.example.plugins.routes.industrial.delete.property

import com.example.dao.dao
import com.example.util.BasicApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteIndustrialProperty() {
    delete("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val isDeleted = dao.deleteIndustrialProperty(id)
            if (isDeleted) {
                call.respond(HttpStatusCode.OK, BasicApiResponse(true, "The Industrial was deleted successfully."))
            } else {
                call.respond(HttpStatusCode.NotFound, "no property found")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid or missing property.")
        }

    }
}