package com.example.plugins.routes.touristic.delete.image

import com.example.dao.dao
import com.example.util.BasicApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteTouristicImage() {
    delete("removeImage/{propertyId}/{id}") {
        val propertyId = call.parameters["propertyId"]?.toIntOrNull()
        val imageId = call.parameters["id"]?.toIntOrNull()
        if (propertyId == null || imageId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
        if (dao.deleteImageByPropertyId(imageId)) {
            call.respond(HttpStatusCode.OK, BasicApiResponse(true,"Image deleted successfully."))  // Successfully deleted
        } else {
            call.respond(HttpStatusCode.NotFound) // Image not found
        }
    }
}