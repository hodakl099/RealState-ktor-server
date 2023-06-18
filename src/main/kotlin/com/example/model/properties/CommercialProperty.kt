package com.example.model.properties

import com.example.model.Properties
import com.example.model.Property
import org.jetbrains.exposed.sql.Table
import javax.management.monitor.StringMonitor

data class CommercialProperty(
    val property: Property,
    val propertyType: String,
    val squareFoot : Double,
    val trafficCount : String,
    val zoningInfo : String,
    val amenities : String,
    val price : Double
)

object CommercialProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val propertyType = varchar("property_type", 256)
    val squareFoot = double("squareFoot")
    val trafficCount = varchar("trafficCount",256)
    val zoningInfo = varchar("zoningInfo",1024)
    val amenities = varchar("amenities",1024)
    val price = double("price")

}



