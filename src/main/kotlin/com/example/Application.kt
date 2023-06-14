package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.util.DatabaseFactory
import org.h2.engine.Database

fun main(args: Array<String>): Unit =
    EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting()
    configureGraphQl()
    configureTemplating()
//    configureDatabases()
//    configureSockets()
    configureMonitoring()
}
