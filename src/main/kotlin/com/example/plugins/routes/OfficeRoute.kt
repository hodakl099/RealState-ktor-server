package com.example.plugins.routes

import com.example.dao.dao
import com.example.model.Property
import com.example.model.properties.OfficeProperty
import com.example.util.BasicApiResponse
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream

fun Route.createOfficeRoute() {
    route("/office") {
        post {
            val multiPart = call.receiveMultipart()
            var layoutType: String? = null
            var squareFoot: Double? = null
            var floorNumber: Int? = null
            var amenities: String? = null
            var accessibility: String? = null
            var location: String? = null
            var agentContact: String? = null
            var price: Double? = null
            val videoURLs = mutableListOf<String>()
            val imageURLs = mutableListOf<String>()

            multiPart.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name?.isEmpty() == true) {
                            call.respond(HttpStatusCode.OK, BasicApiResponse(false,"${part.name} can't be empty"))
                        }
                        when (part.name) {
                            "layoutType" -> layoutType = part.value
                            "squareFoot" -> squareFoot = part.value.toDoubleOrNull()
                            "floorNumber" -> floorNumber = part.value.toIntOrNull()
                            "amenities" -> amenities = part.value
                            "accessibility" -> accessibility = part.value
                            "location" -> location = part.value
                            "agentContact" -> agentContact = part.value
                            "price" -> price = part.value.toDoubleOrNull()
                        }
                    }
                    is PartData.FileItem -> {
                        if (part.name == "video" || part.name == "image") {
                            val fileBytes = part.streamProvider().readBytes()
                            val creds = withContext(Dispatchers.IO) {
                                GoogleCredentials.fromStream(FileInputStream("src/main/resources/verdant-option-390012-977b2708f8e5.json"))
                            }
                            val storage = StorageOptions.newBuilder().setCredentials(creds).build().service

                            // The name of your bucket
                            val bucketName = "tajaqar"

                            // Create a blobId with the name of the file
                            val blobId = part.originalFileName?.let { BlobId.of(bucketName, it) }

                            // Create a blobInfo
                            val blobInfo = blobId?.let { BlobInfo.newBuilder(it).build() }

                            // Upload the file to the bucket
                            blobInfo?.let { storage?.create(it, fileBytes) }

                            // Get the download URL
                            val filePath = blobId?.let { storage?.get(it)?.mediaLink }

                            if (part.name == "video") videoURLs.add(filePath ?: "")
                            else imageURLs.add(filePath ?: "")
                        }
                    }
                    else -> return@forEachPart
                }
                part.dispose()
            }

            val officeProperty = OfficeProperty(
                property = Property(
                    id = 0, // This value will be replaced by autoincrement id
                    agentContact = agentContact ?: "",
                    price = price ?: 0.0,
                    images = imageURLs,
                    videos = videoURLs,
                    location = location ?: "",
                ),
                layoutType = layoutType ?: "",
                squareFoot = squareFoot ?: 0.0,
                floorNumber = floorNumber ?: 0,
                amenities = amenities ?: "",
                accessibility = accessibility ?: "",
            )

            dao.addOfficeProperty(officeProperty, imageURL = videoURLs, videoURL = imageURLs)
            call.respond(HttpStatusCode.OK, BasicApiResponse(true,"New Office Property Added Successfully."))
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if(id != null) {
                val isDeleted = dao.deleteOfficeProperty(id)
                if (isDeleted) {
                    call.respond(HttpStatusCode.OK, BasicApiResponse(true,"The Office was deleted successfully."))
                }else {
                    call.respond(HttpStatusCode.NotFound,"no property found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing property.")
            }
        }
        get("property/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if(id != null) {
                val property = dao.getOfficeProperty(id)
                if (property != null) {
                    call.respond(HttpStatusCode.OK, property)
                }else {
                    call.respond(HttpStatusCode.NotFound, "No property found with the provided ID.")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing ID.")
            }
        }
        put("updateProperty/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val multiPart = call.receiveMultipart()
            if(id != null) {
                var layoutType: String? = null
                var squareFoot: Double? = null
                var floorNumber: Int? = null
                var amenities: String? = null
                var accessibility: String? = null
                var location: String? = null
                var agentContact: String? = null
                var price: Double? = null
                val videoURLs = mutableListOf<String>()
                val imageURLs = mutableListOf<String>()

                multiPart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            if (part.name?.isEmpty() == true) {
                                call.respond(HttpStatusCode.OK, BasicApiResponse(false,"${part.name} can't be empty"))
                            }
                            when (part.name) {
                                "layoutType" -> layoutType = part.value
                                "squareFoot" -> squareFoot = part.value.toDoubleOrNull()
                                "floorNumber" -> floorNumber = part.value.toIntOrNull()
                                "amenities" -> amenities = part.value
                                "accessibility" -> accessibility = part.value
                                "location" -> location = part.value
                                "agentContact" -> agentContact = part.value
                                "price" -> price = part.value.toDoubleOrNull()
                            }
                        }
                        is PartData.FileItem -> {
                            if (part.name == "video" || part.name == "image") {
                                val fileBytes = part.streamProvider().readBytes()
                                val creds = withContext(Dispatchers.IO) {
                                    GoogleCredentials.fromStream(FileInputStream("src/main/resources/verdant-option-390012-977b2708f8e5.json"))
                                }
                                val storage = StorageOptions.newBuilder().setCredentials(creds).build().service

                                // The name of your bucket
                                val bucketName = "tajaqar"

                                // Create a blobId with the name of the file
                                val blobId = part.originalFileName?.let { BlobId.of(bucketName, it) }

                                // Create a blobInfo
                                val blobInfo = blobId?.let { BlobInfo.newBuilder(it).build() }

                                // Upload the file to the bucket
                                blobInfo?.let { storage?.create(it, fileBytes) }

                                // Get the download URL
                                val filePath = blobId?.let { storage?.get(it)?.mediaLink }

                                if (part.name == "video") videoURLs.add(filePath ?: "")
                                else imageURLs.add(filePath ?: "")
                            }
                            val officeProperty = OfficeProperty(
                                    property = Property(
                                            id = 0, // This value will be replaced by autoincrement id
                                            agentContact = agentContact ?: "",
                                            price = price ?: 0.0,
                                            images = imageURLs,
                                            videos = videoURLs,
                                            location = location ?: "",
                                    ),
                                    layoutType = layoutType ?: "",
                                    squareFoot = squareFoot ?: 0.0,
                                    floorNumber = floorNumber ?: 0,
                                    amenities = amenities ?: "",
                                    accessibility = accessibility ?: "",
                            )
                            val isUpdated = dao.updateOfficeProperty(id, officeProperty)
                            if (isUpdated) {
                                call.respond(HttpStatusCode.OK,BasicApiResponse(true,"Property updated successfully."))
                            } else {
                                call.respond(HttpStatusCode.BadRequest, "Invalid or missing ID.")
                            }

                        }
                        else -> return@forEachPart
                    }
                    part.dispose()
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing ID.")
            }
        }
        delete("removeImage/{id}") {
            val propertyId = call.parameters["propertyId"]?.toIntOrNull()
            val imageId = call.parameters["imageId"]?.toIntOrNull()
            if (propertyId == null || imageId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            if (dao.deleteImageByPropertyId(imageId)) {
                call.respond(HttpStatusCode.OK, BasicApiResponse(true, "deleted successfully!")) // Successfully deleted
            } else {
                call.respond(HttpStatusCode.NotFound) // Image not found
            }
        }
        delete("removeVideo/{id}") {
            val propertyId = call.parameters["propertyId"]?.toIntOrNull()
            val videoId = call.parameters["videoId"]?.toIntOrNull()
            if (propertyId == null || videoId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            if (dao.deleteVideosById(videoId)) {
                call.respond(HttpStatusCode.NoContent) // Successfully deleted
            } else {
                call.respond(HttpStatusCode.NotFound) // Video not found
            }
        }
    }

}