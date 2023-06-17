package com.example.data.models

import org.jetbrains.exposed.sql.Table

data class RealState(val id: Int, val title: String, val description: String,
                     val latitude: Double, val longitude: Double, var images: List<Image>,
                     var videos: List<Video>)

object RealStates : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title",128)
    val description = varchar("description", 1028)
    val latitude = double("latitude")
    val longitude = double("longitude")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
