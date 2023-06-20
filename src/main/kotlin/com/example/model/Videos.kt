package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable


@Serializable
data class Video(
        val videoId : Int,
        val propertyId: Int,
        val url: String
)


object Videos : IntIdTable() {
    val propertyId = integer("propertyId").references(Properties.id)
    val url = varchar("url", 2048)
    val videoId = integer("videoId").autoIncrement()
}