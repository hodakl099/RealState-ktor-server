package com.example.model

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

data class Property(
    val id: Int,
    val agentContact: String,
    val price: BigDecimal
)

object Properties : Table() {
    val id = integer("id").autoIncrement()
    val agentContact = varchar("agent_contact", 256)
    val price = decimal("price", 12, 2)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
