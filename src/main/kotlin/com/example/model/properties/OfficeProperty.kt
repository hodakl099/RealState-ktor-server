package com.example.model.properties

import com.example.model.Properties
import com.example.model.Property
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * Office Property
 */
@Serializable
data class OfficeProperty(
    val property: Property,
    val propertyType: String,
    val layoutType: String,
    val acres : Int,
    val floorNumber : Int,
    val amenities  : String,
    val accessibility : String,
)

object OfficeProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val layoutType = varchar("layout_type", 256)
    val propertyType = varchar("property_type", 256)
    val acres = integer("acres")
    val floorNumber = integer("floorNumber")
    val accessibility = varchar("accessibility",1024)
    val amenities = varchar("amenities",1024)
}




