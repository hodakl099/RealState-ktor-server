package com.example.model

import org.jetbrains.exposed.dao.id.IntIdTable



data class Image(val propertyId: Int, val url: String)

object Images : IntIdTable() {
    val propertyId = integer("propertyId").references(Properties.id)
    val url = varchar("url", 2048)
}