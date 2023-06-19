package com.example.model

import org.jetbrains.exposed.dao.id.IntIdTable


data class Video(
        val propertyId: Int,
        val url: String
)


object Videos : IntIdTable() {
    val propertyId = integer("propertyId").references(Properties.id)
    val url = varchar("url", 2048)
}