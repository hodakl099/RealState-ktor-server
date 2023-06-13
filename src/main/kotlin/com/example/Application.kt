package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.util.DatabaseFactory
import org.h2.engine.Database

fun main() {
    embeddedServer(Netty, port = 4000, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting()
    configureGraphQl()
//    configureDatabases()
//    configureSockets()
//    configureMonitoring()
}
