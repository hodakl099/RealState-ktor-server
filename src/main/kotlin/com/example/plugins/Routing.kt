package com.example.plugins


import com.example.plugins.routes.agricultural.createAgriculturalRoute
import com.example.plugins.routes.commercial.createCommercialRoute
import com.example.plugins.routes.industrial.createIndustrialRoute
import com.example.plugins.routes.office.createOfficeRoute
import com.example.plugins.routes.residential.createResidentialRoute
import com.example.plugins.routes.touristic.createTouristicRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


/**
 * Routing block
 */
fun Application.configureRouting() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.localizedMessage)
        }
        exception<NotFoundException> {call, cause ->
            call.respond(HttpStatusCode.NotFound, cause.localizedMessage)
        }
    }

    routing {
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

