package com.example.data.models

import org.jetbrains.exposed.sql.Table

data class Video(val id: Int, val realStateId: Int, val url: String)

object Videos : Table() {
    val id = integer("id").autoIncrement()
    val realStateId = integer("realStateId").references(RealStates.id)
    val url = varchar("url", 2048)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}