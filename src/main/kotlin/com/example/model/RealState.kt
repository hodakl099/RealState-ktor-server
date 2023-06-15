package com.example.model

import org.jetbrains.exposed.sql.Table

data class RealState(val id : Int, val title : String, val description : String, val latitude : Double,
    val longitude : Double, val videoURL : String, val imageURL : String)


object RealStates : Table() {
    val id  = integer("id").autoIncrement()
    val title = varchar("title",128)
    val description = varchar("description", 1028)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val videoURL = varchar("videoURL",2048)
    val imageURL = varchar("imageURL",2048)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
