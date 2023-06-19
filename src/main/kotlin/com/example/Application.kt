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
        val allStates = dao.getAllCommercialProperties()
        val allStatesAgricultural = dao.getAllAgriculturalProperties()
        println("The database" + allRealStates)
        println("The commercial real estate" + allStates)
        println("The commercial real estate" + allStates)
        println("The allStatesAgricultural real estate" + allStatesAgricultural)
    }
    configureSerialization()

    configureRouting()
//    configureGraphQl()
    configureTemplating()
    configureSockets()
    configureMonitoring()
}
