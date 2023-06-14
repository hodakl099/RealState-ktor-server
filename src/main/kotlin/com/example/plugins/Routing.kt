package com.example.plugins

import com.example.data.dao
import com.example.model.RealState
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import java.awt.Frame
import java.io.File


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
                var title : String? = null
                var description : String? = null
                var videoURL : String? = null
                var imageURL : String? = null
                var latitude : Double? = null
                var longitude : Double? = null

                multiPart.forEachPart {part ->
                    when(part) {
                        is PartData.FormItem -> {
                            when(part.name) {
                                "title" -> title = part.value
                                "description" -> description = part.value
                                "latitude" -> latitude = part.value.toDoubleOrNull()
                                "longitude" -> longitude = part.value.toDoubleOrNull()
                            }

                        }
                        is PartData.FileItem -> {
                            if (part.name == "videoURL" || part.name == "imageURL") {
                                val fileBytes = part.streamProvider().readBytes()
                                val filePath = "uploads/${part.originalFileName}"
                                File(filePath).writeBytes(fileBytes)
                                if (part.name == "videoURL") videoURL = filePath
                                else imageURL = filePath
                            }
                        }
                        else -> return@forEachPart
                    }
                    part.dispose()
                }
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
                    call.respondRedirect("/articles")
                }
            }
        }
    }

}
