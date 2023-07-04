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
    val layoutType: String,
    val squareFoot : Double,
    val floorNumber : Int,
    val amenities  : String,
    val accessibility : String,
)

object OfficeProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val layoutType = varchar("layout_type", 256)
    val squareFoot = double("squareFoot")
    val floorNumber = integer("floorNumber")
    val accessibility = varchar("accessibility",1024)
    val amenities = varchar("amenities",1024)
}




