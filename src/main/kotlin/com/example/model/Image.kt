package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable


/**
 * Each image refrence to a property.
 */
@Serializable
data class Image(val imageId : Int,val propertyId: Int, val url: String,val objectName : String)

object Images : IntIdTable() {
    val propertyId = integer("propertyId").references(Properties.id)
    val url = varchar("url", 2048)
    val imageId  = integer("imageId").autoIncrement()
    val objectName = varchar("objectName",256)
}


