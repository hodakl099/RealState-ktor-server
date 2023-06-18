package com.example.model.properties

import com.example.model.Properties
import com.example.model.Property
import org.jetbrains.exposed.sql.Table
data class OfficeProperty(
    val property: Property,
    val layoutType: String,
    val squareFoot : String,
    val floorNumber : Int,
    val amenities  : String,
    val accessibility : String,
    val price : Double,
)

object OfficeProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val layoutType = varchar("layout_type", 256)
    val squareFoot = double("squareFoot")
    val floorNumber = integer("floorNumber")
    val accessibility = varchar("accessibility",1024)
    val amenities = varchar("amenities",1024)
    val price = double("price")
}




