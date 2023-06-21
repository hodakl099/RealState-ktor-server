package com.example.plugins.routes.commercial.delete.image

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

fun Route.deleteCommercialImage() {
    delete("removeImage/{propertyId}/{id}") {
        val propertyId = call.parameters["propertyId"]?.toIntOrNull()
        val imageId = call.parameters["id"]?.toIntOrNull()
        if (propertyId == null || imageId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
        val image = dao.getImageById(imageId)

        if (image != null) {
            val creds = withContext(Dispatchers.IO) {
                GoogleCredentials.fromStream(FileInputStream("src/main/resources/verdant-option-390012-977b2708f8e5.json"))
            }
            val storage = StorageOptions.newBuilder().setCredentials(creds).build().service

            val blobId = BlobId.of("tajaqar", image.objectName)
            storage.delete(blobId)
        }
        if (dao.deleteImageByPropertyId(imageId)) {
            call.respond(HttpStatusCode.OK, BasicApiResponse(true,"Image deleted successfully."))
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }
}