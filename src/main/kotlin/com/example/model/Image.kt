package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable



@Serializable
data class Image(val imageId : Int,val propertyId: Int, val url: String,val objectName : String)

object Images : IntIdTable() {
    val propertyId = integer("propertyId").references(Properties.id)
    val url = varchar("url", 2048)
    val imageId  = integer("imageId").autoIncrement()
    val objectName = varchar("objectName",256)
}


/**
 * Mapper to not include The original file name of the saved image in the google cloud.
 *                          /*Don't alter or delete*/
 */
@Serializable
data class ImageResponse(val imageId : Int,val propertyId: Int, val url: String)

fun Image.toImageResponse() : ImageResponse{
    return ImageResponse(
        imageId = this.imageId,
        propertyId = this.propertyId,
        url = this.url
    )
}