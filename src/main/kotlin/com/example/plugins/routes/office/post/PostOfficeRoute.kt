package com.example.plugins.routes.office.post

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
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

fun Route.postOfficeProperty() {
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
        val videoURLs = mutableListOf<Pair<String, String>>()
        val imageURLs = mutableListOf<Pair<String, String>>()

        multiPart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name?.isEmpty() == true) {
                        call.respond(HttpStatusCode.OK, BasicApiResponse(false, "${part.name} can't be empty"))
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
                        try {
                            val creds = withContext(Dispatchers.IO) {
                                GoogleCredentials.fromStream(FileInputStream("src/main/resources/verdant-option-390012-977b2708f8e5.json"))
                            }
                            val storage = StorageOptions.newBuilder().setCredentials(creds).build().service


                            val bucketName = "tajaqar"

                            val blobId = part.originalFileName?.let { BlobId.of(bucketName, it) }


                            val blobInfo = blobId?.let { BlobInfo.newBuilder(it).build() }

                            blobInfo?.let { storage?.create(it, fileBytes) }

                            val filePath = blobId?.let { storage?.get(it)?.mediaLink }

                            val urlAndName = Pair(filePath ?: "", part.originalFileName ?: "")

                            if (part.name == "video") videoURLs.add(urlAndName)
                            else imageURLs.add(urlAndName)
                        } catch (e: Exception) {
                            call.respond(HttpStatusCode.InternalServerError, BasicApiResponse(false,"something went wrong while uploading the file."))
                            return@forEachPart
                        } catch (e: IllegalArgumentException) {
                            call.respond(HttpStatusCode.BadRequest, BasicApiResponse(false,e.message ?: "Invalid input."))
                            return@forEachPart
                        }

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
                images = imageURLs.map { Image(url = it.first, propertyId = 0, imageId = 0, objectName = it.second) },
                videos = videoURLs.map { Video(url = it.first, propertyId = 0, videoId = 0, objectName = it.second) },
                location = location ?: "",
            ),
            layoutType = layoutType ?: "",
            squareFoot = squareFoot ?: 0.0,
            floorNumber = floorNumber ?: 0,
            amenities = amenities ?: "",
            accessibility = accessibility ?: "",
        )

        dao.addOfficeProperty(officeProperty, imageURL = videoURLs, videoURL = imageURLs)
        call.respond(HttpStatusCode.OK, BasicApiResponse(true, "New Office Property Added Successfully."))
    }
}