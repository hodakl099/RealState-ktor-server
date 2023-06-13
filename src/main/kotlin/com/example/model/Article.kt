package com.example.model

import org.jetbrains.exposed.sql.Table

data class Article(val id : Int, val title : String, val body : String)



object Articles : Table() {

    var id  = integer("id").autoIncrement()
    var title = varchar("title",128)
    var body = varchar("body", 1024)

    override val primaryKey: PrimaryKey? = PrimaryKey(id)

}
