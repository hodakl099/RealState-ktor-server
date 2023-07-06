package com.example.plugins.routes.residential.post

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.Videos
import com.example.model.properties.ResidentialProperty
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
import org.litote.kmongo.util.idValue
import java.io.FileInputStream

fun Route.postResidentialProperty() {
    post("/Add") {
        val multiPart = call.receiveMultipart()
        var propertyType: String? = null
        var acres: Int? = null
        var bedrooms: Int? = null
        var bathrooms: Int? = null
        var amenities: String? = null
        var parking: String? = null
        var location: String? = null
        var agentContact: String? = null
        var price: Int? = null
        val videoURLs = mutableListOf<Pair<String, String>>()
        val imageURLs = mutableListOf<Pair<String, String>>()


        multiPart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name?.isEmpty() == true) {
                        call.respond(HttpStatusCode.OK, BasicApiResponse(false, "${part.name} can't be empty"))
                    }
                    when (part.name) {
                        "agentContact" ->  {
                            agentContact = part.value
                            println("Received agentContact: $agentContact")
                        }
                        "price" -> {
                            price = part.value.toIntOrNull()
                            println("Received price: $price")
                        }
                        "propertyType" -> {
                            propertyType = part.value
                            println("Received propertyType: $propertyType")
                        }
                        "acres" -> {
                            acres = part.value.toIntOrNull()
                            println("Received acres: $acres")
                        }
                        "bedrooms" -> {
                            bedrooms = part.value.toIntOrNull()
                            println("Received bedrooms: $bedrooms")
                        }
                        "bathrooms" -> {
                            bathrooms = part.value.toIntOrNull()
                            println("Received bathrooms: $bathrooms")
                        }
                        "amenities" -> {
                            amenities = part.value
                            println("Received amenities: $amenities")
                        }
                        "parking" ->{
                            parking = part.value
                            println("Received parking: $parking")
                        }
                        "location" ->  {
                            location = part.value
                            println("Received location: $location")
                        }
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

        val residentialProperty = ResidentialProperty(
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
            bedrooms = bedrooms ?: 0,
            bathrooms = bathrooms ?: 0,
            amenities = amenities ?: "",
            parking = parking ?: "",
        )

        dao.addResidentialProperty(residentialProperty, imageURL = imageURLs, videoURL = videoURLs)
        call.respond(
            HttpStatusCode.OK,
            BasicApiResponse(true, "New Residential Property Added Successfully ${Videos.idValue}.")
        )
    }
}