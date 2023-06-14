package com.example.plugins

import com.example.data.dao
import com.example.model.RealState
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import java.awt.Frame


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
        post {
            val fromParameter = call.receiveParameters()
            val title = fromParameter.getOrFail<String>("title")
            val description = fromParameter.getOrFail<String>("description")
            val videoURL = fromParameter.getOrFail<String>("videoURL")
            val imageURL = fromParameter.getOrFail<String>("imageURL")
            val body = fromParameter.getOrFail<String>("body")
            val body = fromParameter.getOrFail<String>("body")
            val description = dao.addNewRealState(title = title ?: "",description = body ?: "", videoURL = videoURL, imageURL = )
            call.respondRedirect("/articles/${article?.id}")
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
                    val title = fromParameters.getOrFail("title")
                    val body = fromParameters.getOrFail("body")
                    dao.editRealState(id,title,body)
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
