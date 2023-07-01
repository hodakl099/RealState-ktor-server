package com.example

import com.example.dao.dao
import io.ktor.server.application.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.util.DatabaseFactory
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>): Unit =
    EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    runBlocking {
        val allRealStates = dao.getAllAgriculturalProperties()
        println("The  real estate" + allRealStates + "!!!!")
    }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.XForwardedProto)
        allowCredentials = true
        allowNonSimpleContentTypes = true
    }
    configureSerialization()
    configureRouting()
//    configureGraphQl()
    configureTemplating()
    configureSockets()
    configureMonitoring()
}
