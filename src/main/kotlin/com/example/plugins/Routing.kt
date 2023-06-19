package com.example.plugins


import com.example.dao.dao
import com.example.model.Property
import com.example.model.ResidentialProperty
import com.example.model.properties.AgriculturalProperty
import com.example.plugins.routes.*
import com.example.util.BasicApiResponse
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream


fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {


        //post routes
        route("/properties") {
            createCommercialRoute()
            createIndustrialRoute()
            createOfficeRoute()
            createAgriculturalRoute()
            createTouristicRoute()
            createResidentialRoute()
        }



        get("/properties") {
            call.respond(io.ktor.websocket.Frame.Text("wtf"))
        }
        route("/properties") {
            get {
                call.respondText("Upload endpoint")
            }

//        get("{id}") {
//            val id = call.parameters.getOrFail<Int>("id").toInt()
//            call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.realState(id))))
//        }
//        get("{id}/edit") {
//            val id = call.parameters.getOrFail<Int>("id").toInt()
//            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.realState(id))))
//        }
            post("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val fromParameters = call.receiveParameters()
                when (fromParameters.getOrFail("_action")) {
                    "update" -> {
                        val title = fromParameters.getOrFail<String>("title")
                        val description = fromParameters.getOrFail<String>("description")
                        val videoURL = fromParameters.getOrFail<String>("videoURL")
                        val imageURL = fromParameters.getOrFail<String>("imageURL")
                        val latitude = fromParameters.getOrFail<Double>("latitude")
                        val longitude = fromParameters.getOrFail<Double>("longitude")
//                    dao.editRealState(id = id,title = title,description = description,imageURL= imageURL, videoURL = videoURL, latitude = latitude, longitude = longitude)
                        call.respondRedirect("/articles/$id")
                    }

                    "delete" -> {
//                    dao.deleteRealState(id)
                        call.respondRedirect("/realStates")
                    }
                }
            }
        }

    }
}

