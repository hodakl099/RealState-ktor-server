package com.example.data
import com.example.model.RealState
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.*

object Database {
    val client = KMongo.createClient().coroutine
    val database = client.getDatabase("realStateDatabase")
    val realStateCollection = database.getCollection<RealState>("realStates")

}