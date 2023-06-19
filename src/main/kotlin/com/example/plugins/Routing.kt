package com.example.plugins


import com.example.plugins.routes.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


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

    }
}

