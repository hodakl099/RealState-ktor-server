package com.example.model

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

data class Property(
    val id: Int,
    val agentContact: String,
    val price: Double,
    val images : List<String>,
    val video : List<String>
)

object Properties : Table() {
    val id = integer("id").autoIncrement()
    val agentContact = varchar("agent_contact", 256)
    val price = double("price")
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
