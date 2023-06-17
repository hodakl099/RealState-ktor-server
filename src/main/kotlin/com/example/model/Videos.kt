package com.example.model

import org.jetbrains.exposed.sql.Table

data class Video(
    val id: Int,
    val propertyId: Int,
    val url: String
)


object Videos : Table() {
    val id = integer("id").autoIncrement()
    val propertyId = integer("propertyId").references(Properties.id)
    val url = varchar("url", 2048)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}