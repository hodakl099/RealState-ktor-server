package com.example.plugins.routes

import com.example.dao.dao
import com.example.model.Property
import com.example.model.properties.CommercialProperty
import com.example.model.properties.IndustrialProperty
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


fun Route.createCommercialRoute() {
    route("/commercial") {
            post {
                val multiPart = call.receiveMultipart()
                var propertyType: String? = null
                var squareFoot: Double? = null
                var trafficCount: String? = null
                var zoningInfo: String? = null
                var amenities: String? = null
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
                                "propertyType" -> propertyType = part.value
                                "squareFoot" -> squareFoot = part.value.toDoubleOrNull()
                                "trafficCount" -> trafficCount = part.value
                                "zoningInfo" -> zoningInfo = part.value
                                "amenities" -> amenities = part.value
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

                val commercialProperty = CommercialProperty(
                    property = Property(
                        id = 0, // This value will be replaced by autoincrement id
                        agentContact = agentContact ?: "",
                        price = price ?: 0.0,
                        images = imageURLs,
                        videos = videoURLs,
                        location = location ?: "",
                    ),
                    propertyType = propertyType ?: "",
                    squareFoot = squareFoot ?: 0.0,
                    trafficCount = trafficCount ?: "",
                    zoningInfo = zoningInfo ?: "",
                    amenities = amenities ?: "",
                )

                dao.addCommercialProperty(commercialProperty, imageURL = videoURLs, videoURL = imageURLs)
                call.respond(HttpStatusCode.OK, BasicApiResponse(true,"New Commercial Property Added Successfully."))
            }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if(id != null) {
                val isDeleted = dao.deleteCommercialProperty(id)
                if (isDeleted) {
                    call.respond(HttpStatusCode.OK, BasicApiResponse(true,"The Commercial was deleted successfully."))
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
                val property = dao.getCommercialProperty(id)
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
                var propertyType: String? = null
                var squareFoot: Double? = null
                var trafficCount: String? = null
                var zoningInfo: String? = null
                var amenities: String? = null
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
                                "propertyType" -> propertyType = part.value
                                "squareFoot" -> squareFoot = part.value.toDoubleOrNull()
                                "trafficCount" -> trafficCount = part.value
                                "zoningInfo" -> zoningInfo = part.value
                                "amenities" -> amenities = part.value
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
                            val commercialProperty = CommercialProperty(
                                    property = Property(
                                            id = 0, // This value will be replaced by autoincrement id
                                            agentContact = agentContact ?: "",
                                            price = price ?: 0.0,
                                            images = imageURLs,
                                            videos = videoURLs,
                                            location = location ?: "",
                                    ),
                                    propertyType = propertyType ?: "",
                                    squareFoot = squareFoot ?: 0.0,
                                    trafficCount = trafficCount ?: "",
                                    zoningInfo = zoningInfo ?: "",
                                    amenities = amenities ?: "",
                            )
                            val isUpdated = dao.updateCommercialProperty(id, commercialProperty)
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
    }
}