package com.example.plugins

import com.example.data.dao
import com.example.model.CreateRealState
import com.example.util.BasicApiResponse
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.net.http.HttpRequest


fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/properties") {
            call.respond(io.ktor.websocket.Frame.Text("wtf"))
        }
        get {
            call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to dao.allRealStates())))
        }
        route("/upload") {
            get {
                call.respondText("Upload endpoint")
            }

            post {
                val multiPart = call.receiveMultipart()
                var title: String? = null
                var description: String? = null
                var videoURL: String? = null
                var imageURL: String? = null
                var latitude: Double? = null
                var longitude: Double? = null

                multiPart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            if (part.name?.isEmpty() == true) {
                                call.respond(HttpStatusCode.OK
                                    ,BasicApiResponse(false,"${part.name} can't be empty")
                                )
                            }
                            when (part.name) {
                                "title" -> title = part.value
                                "description" -> description = part.value
                                "latitude" -> latitude = part.value.toDoubleOrNull()
                                "longitude" -> longitude = part.value.toDoubleOrNull()
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

                                if (part.name == "video") videoURL = filePath
                                else imageURL = filePath
                            }
                        }
                        else -> return@forEachPart
                    }
                    part.dispose()
                }
                dao.addNewRealState(
                    title = title ?: "",
                    description = description ?: "",
                    latitude = latitude ?: 0.0,
                    longitude = longitude ?: 0.0,
                    videoURL = videoURL ?: "",
                    imageURL = imageURL ?: ""
                )
                print(dao.allRealStates())
                call.respond(HttpStatusCode.OK,BasicApiResponse(true,"New RealState Added Successfully."))
            }
        }

        get("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.realState(id))))
        }
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.realState(id))))
        }
        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val fromParameters = call.receiveParameters()
            when(fromParameters.getOrFail("_action")) {
                "update" -> {
                    val title = fromParameters.getOrFail<String>("title")
                    val description = fromParameters.getOrFail<String>("description")
                    val videoURL = fromParameters.getOrFail<String>("videoURL")
                    val imageURL = fromParameters.getOrFail<String>("imageURL")
                    val latitude = fromParameters.getOrFail<Double>("latitude")
                    val longitude = fromParameters.getOrFail<Double>("longitude")
                    dao.editRealState(id = id,title = title,description = description,imageURL= imageURL, videoURL = videoURL, latitude = latitude, longitude = longitude)
                    call.respondRedirect("/articles/$id")
                }
                "delete" -> {
                    dao.deleteRealState(id)
                    call.respondRedirect("/realStates")
                }
            }
        }
    }

}

