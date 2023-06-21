package com.example.plugins.routes.office.delete.video

import com.example.dao.dao
import com.example.util.BasicApiResponse
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.StorageOptions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream

fun Route.deleteOfficeVideo() {
    delete("removeVideo/{propertyId}/{videoId}") {
        val propertyId = call.parameters["propertyId"]?.toIntOrNull()
        val videoId = call.parameters["videoId"]?.toIntOrNull()

        if (propertyId == null || videoId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
        val video = dao.getVideoById(videoId)

        if (video != null) {
            val creds = withContext(Dispatchers.IO) {
                GoogleCredentials.fromStream(FileInputStream("src/main/resources/verdant-option-390012-977b2708f8e5.json"))
            }
            val storage = StorageOptions.newBuilder().setCredentials(creds).build().service

            val blobId = BlobId.of("tajaqar", video.objectName)
            storage.delete(blobId)
        }
        if (dao.deleteVideosById(videoId)) {
            call.respond(HttpStatusCode.OK, BasicApiResponse(true,"Video deleted successfully."))
        } else {
            call.respond(HttpStatusCode.NotFound, BasicApiResponse(false,"Invalid Video!"))
            return@delete
        }
    }
}