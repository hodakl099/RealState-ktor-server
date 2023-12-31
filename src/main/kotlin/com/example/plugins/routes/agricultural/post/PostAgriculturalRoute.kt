package com.example.plugins.routes.agricultural.post

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.properties.AgriculturalProperty
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

fun Route.postAgriculturalRoute() {
    post("/Add") {
        val multiPart = call.receiveMultipart()
        var propertyType: String? = null
        var acres: Int? = null
        var buildings: String? = null
        var crops: String? = null
        var waterSources: String? = null
        var soilType: String? = null
        var equipment: String? = null
        var location: String? = null
        var price: Int? = null
        var agentContact: String? = null
        val videoURLs = mutableListOf<Pair<String,String>>()
        val imageURLs = mutableListOf<Pair<String,String>>()


        multiPart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "agentContact" -> agentContact = part.value
                        "price" -> price = part.value.toIntOrNull() ?: throw java.lang.IllegalArgumentException("Invalid or missing price.")
                        "propertyType" -> propertyType = part.value
                        "acres" -> acres = part.value.toIntOrNull() ?: throw java.lang.IllegalArgumentException("Invalid or missing Area.")
                        "buildings" -> buildings = part.value
                        "crops" -> crops = part.value
                        "soilType" -> soilType = part.value
                        "equipment" -> equipment = part.value
                        "location" -> location = part.value
                        "waterSources" -> waterSources = part.value
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

        val agriculturalProperty = AgriculturalProperty(
                property = Property(
                        id = 0, // This value will be replaced by autoincrement id
                        agentContact = agentContact ?: "",
                        price = price ?: 100,
                        images = imageURLs.map { Image(url = it.first , propertyId = 0, imageId = 0, objectName = it.second) },
                        videos = videoURLs.map { Video(url = it.first , propertyId = 0, videoId = 0, objectName =it.second) },
                        location = location ?: ""
                ),
                propertyType = propertyType ?: "",
                acres = acres  ?: 10,
                buildings = buildings ?: "",
                crops = crops ?: "",
                waterSources = waterSources ?: "",
                soilType = soilType ?: "",
                equipment = equipment ?: ""
        )
        dao.addAgriculturalProperty(agriculturalProperty, videoURL = videoURLs, imageURL = imageURLs)
        call.respond(HttpStatusCode.OK, BasicApiResponse(true,"New Agricultural Property Added Successfully ${agriculturalProperty.property.id}."))
    }
}