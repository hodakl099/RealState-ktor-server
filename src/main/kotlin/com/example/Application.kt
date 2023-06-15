package com.example

import com.example.data.dao
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
        val allRealStates = dao.allRealStates()
        println(allRealStates)
    }
    configureSerialization()
    configureRouting()
    configureGraphQl()
    configureTemplating()
//    configureDatabases()
    configureSockets()
    configureMonitoring()
}
