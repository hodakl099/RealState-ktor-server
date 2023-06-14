package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.util.DatabaseFactory
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.HttpMethod

fun main(args: Array<String>): Unit =
    EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    install(CORS) {
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost() // will let Ktor answer any request from any origin.
    }
    configureRouting()
    configureGraphQl()
    configureTemplating()
//    configureDatabases()
//    configureSockets()
    configureMonitoring()
}
