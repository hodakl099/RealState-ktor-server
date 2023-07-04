package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * Property Data class represents common fields.
 */
@Serializable
data class Property(
    val id: Int,
    val agentContact: String,
    val price: Int,
    val location: String,
    val images: List<Image>,
    val videos: List<Video>,
)

object Properties : Table() {
    val id = integer("id").autoIncrement()
    val agentContact = varchar("agent_contact", 256)
    val price = integer("price")
    val location = varchar("location", 256)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}


