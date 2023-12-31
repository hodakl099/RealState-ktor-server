package com.example.model.properties

import com.example.model.Properties
import com.example.model.Property
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * Leisure And Touristic Property
 */
@Serializable
data class LeisureAndTouristicProperty(
    val property: Property,
    val propertyType: String,
    val acres : Int,
    val rooms: Int,
    val units : Int,
    val amenities : String,
    val proximityToAttractions : String,
    val occupancyRate : String,
    )

object LeisureAndTouristicProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val propertyType = varchar("property_type", 256)
    val acres = integer("acres")
    val rooms = integer("rooms")
    val units = integer("units")
    val amenities = varchar("amenities",1024)
    val proximityToAttractions = varchar("proximityToAttractions",1024)
    val occupancyRate = varchar("occupancyRate",1024)
}




