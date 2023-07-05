package com.example.plugins.routes.industrial.post

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.properties.IndustrialProperty
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

fun Route.postIndustrialProperty() {
    post {
        val multiPart = call.receiveMultipart()
        var propertyType: String? = null
        var acres: Int? = null
        var zoningInfo: String? = null
        var cellingHeight: Int? = null
        var numberOfLoadingDocks: Int? = null
        var powerCapabilities: String? = null
        var accessToTransportation: String? = null
        var environmentalReports: String? = null
        var location: String? = null
        var agentContact: String? = null
        var price: Int? = null
        val videoURLs = mutableListOf<Pair<String,String>>()
        val imageURLs = mutableListOf<Pair<String,String>>()

        multiPart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name?.isEmpty() == true) {
                        call.respond(HttpStatusCode.OK, BasicApiResponse(false, "${part.name} can't be empty"))
                    }
                    when (part.name) {
                        "propertyType" -> propertyType = part.value
                        "acres" -> acres = part.value.toIntOrNull()
                        "zoningInfo" -> zoningInfo = part.value
                        "cellingHeight" -> cellingHeight = part.value.toIntOrNull()
                        "numberOfLoadingDocks" -> numberOfLoadingDocks = part.value.toIntOrNull()
                        "powerCapabilities" -> powerCapabilities = part.value
                        "accessToTransportation" -> accessToTransportation = part.value
                        "environmentalReports" -> environmentalReports = part.value
                        "location" -> location = part.value
                        "agentContact" -> agentContact = part.value
                        "price" -> price = part.value.toIntOrNull()
                    }
                }

                is PartData.FileItem -> {
                    if (part.name == "video" || part.name == "image") {
                        val fileBytes = part.streamProvider().readBytes()
                        if (fileBytes.isEmpty()) {
                            call.respond(HttpStatusCode.BadRequest, BasicApiResponse(false, "At least one image or video must be provided."))
                        }
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

        if (imageURLs.isEmpty() && videoURLs.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest, BasicApiResponse(false, "At least one image or video must be provided."))
            return@post
        }


        val industrialProperty = IndustrialProperty(
            property = Property(
                id = 0, // This value will be replaced by autoincrement id
                agentContact = agentContact ?: "",
                price = price ?: 0,
                images = imageURLs.map { Image(url = it.first, propertyId = 0, imageId = 0, objectName = it.second) },
                videos = videoURLs.map { Video(url = it.first, propertyId = 0, videoId = 0, objectName = it.second) },
                location = location ?: "",
            ),
            propertyType = propertyType ?: "",
            acres = acres ?: 0,
            zoningInfo = zoningInfo ?: "",
            cellingHeight = cellingHeight ?: 0,
            numberOfLoadingDocks = numberOfLoadingDocks ?: 0,
            powerCapabilities = powerCapabilities ?: "",
            accessToTransportation = accessToTransportation ?: "",
            environmentalReports = environmentalReports ?: ""
        )

        dao.addIndustrialProperty(industrialProperty, imageURL = videoURLs, videoURL = imageURLs)
        call.respond(HttpStatusCode.OK, BasicApiResponse(true, "New Industrial Property Added Successfully."))
    }
}