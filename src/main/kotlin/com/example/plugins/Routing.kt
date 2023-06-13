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
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
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
    }

}
