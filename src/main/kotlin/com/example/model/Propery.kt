package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Property(
    val id: Int,
    val agentContact: String,
    val price: Double,
    val location: String,
    val images : List<String>,
    val videos : List<String>,
)

object Properties : Table() {
    val id = integer("id").autoIncrement()
    val agentContact = varchar("agent_contact", 256)
    val price = double("price")
    val location = varchar("location", 256)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
