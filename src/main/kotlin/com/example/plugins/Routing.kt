package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.apurebase.kgraphql.KGraphQL
import com.example.data.dao
import com.example.model.RealState
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import ktor.graphql.GraphQLRequest
import java.util.UUID


private val realStates = listOf(
    RealState("image1.jpg", "1000 sqft", "Location 1"),
    RealState("image2.jpg", "2000 sqft", "Location 2"),
    RealState("image1.jpg", "3000 sqft", "Location 3")
)


fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/properties") {
            call.respond(realStates)
        }
        get {
            call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to dao.allArticles())))
        }
        post {
            val fromParameter = call.receiveParameters()
            val title = fromParameter.getOrFail<String>("title")
            val body = fromParameter.getOrFail<String>("body")
            val article = dao.addNewArticle(title = title ?: "",body = body ?: "")
            call.respondRedirect("/articles/${article?.id}")
        }
        get("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.article(id))))
        }
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.article(id))))
        }
        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val fromParameters = call.receiveParameters()
            when(fromParameters.getOrFail("_action")) {
                "update" -> {
                    val title = fromParameters.getOrFail("title")
                    val body = fromParameters.getOrFail("body")
                    dao.editArticle(id,title,body)
                    call.respondRedirect("/articles/$id")
                }
                "delete" -> {
                    dao.deleteArticle(id)
                    call.respondRedirect("/articles")
                }
            }
        }
    }

}
