package com.example.plugins.routes.commercial.post

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.properties.CommercialProperty
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

fun Route.postCommercialRoute() {
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
        val videoURLs = mutableListOf<Pair<String, String>>()
        val imageURLs = mutableListOf<Pair<String, String>>()

        multiPart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name?.isEmpty() == true) {
                        call.respond(HttpStatusCode.OK, BasicApiResponse(false, "${part.name} can't be empty"))
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

                        val bucketName = "tajaqar"


                        val blobId = part.originalFileName?.let { BlobId.of(bucketName, it) }


                        val blobInfo = blobId?.let { BlobInfo.newBuilder(it).build() }


                        blobInfo?.let { storage?.create(it, fileBytes) }


                        val filePath = blobId?.let { storage?.get(it)?.mediaLink }


                        val urlAndName = Pair(filePath ?: "", part.originalFileName ?: "")

                        if (part.name == "video") videoURLs.add(urlAndName)
                        else imageURLs.add(urlAndName)
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
                images = imageURLs.map { Image(url = it.first, propertyId = 0, imageId = 0, objectName = it.second) },
                videos = videoURLs.map { Video(url = it.first, propertyId = 0, videoId = 0, objectName = it.second) },
                location = location ?: "",
            ),
            propertyType = propertyType ?: "",
            squareFoot = squareFoot ?: 0.0,
            trafficCount = trafficCount ?: "",
            zoningInfo = zoningInfo ?: "",
            amenities = amenities ?: "",
        )

        dao.addCommercialProperty(commercialProperty, imageURL = videoURLs, videoURL = imageURLs)
        call.respond(HttpStatusCode.OK, BasicApiResponse(true, "New Commercial Property Added Successfully."))
    }
}