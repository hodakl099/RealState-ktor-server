package com.example.plugins.routes.commercial.delete.video

import com.example.dao.dao
import com.example.util.BasicApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteCommercialVideo() {
    delete("removeVideo/{propertyId}/{videoId}") {
        val propertyId = call.parameters["propertyId"]?.toIntOrNull()
        val videoId = call.parameters["videoId"]?.toIntOrNull()
        if (propertyId == null || videoId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
        if (dao.deleteVideosById(videoId)) {
            call.respond(HttpStatusCode.OK, BasicApiResponse(true,"Video deleted successfully."))  // Successfully deleted
        } else {
            call.respond(HttpStatusCode.NotFound, BasicApiResponse(false,"something went wrong!")) // Video not found
        }
    }
}