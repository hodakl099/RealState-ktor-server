package com.example

import com.example.dao.dao
import io.ktor.server.application.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.util.DatabaseFactory
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>): Unit =
    EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    runBlocking {
        val allRealStates = dao.getAllResidentialProperties()
        println("The  real estate" + allRealStates + "!!!!")
    }
    configureSerialization()
    configureRouting()
//    configureGraphQl()
    configureTemplating()
    configureSockets()
    configureMonitoring()
}
