package com.example.model.properties

import com.example.model.Properties
import com.example.model.Property
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * CommercialProperty Property.
 */
@Serializable
data class CommercialProperty(
    val property: Property,
    val propertyType: String,
    val acres : Int,
    val trafficCount : String,
    val zoningInfo : String,
    val amenities : String,
)

object CommercialProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val propertyType = varchar("property_type", 256)
    val acres = integer("acres")
    val trafficCount = varchar("trafficCount",256)
    val zoningInfo = varchar("zoningInfo",1024)
    val amenities = varchar("amenities",1024)
}




