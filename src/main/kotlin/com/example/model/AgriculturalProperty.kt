package com.example.model

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

data class AgriculturalProperty(
    val property: Property,
    val acres: Double,
    val propertyType: String,
    val buildings: String,
    val crops: String,
    val waterSources: String,
    val soilType: String,
    val equipment: String,
)

object AgriculturalProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val acres = double("acres")
    val propertyType = varchar("property_type", 256)
    val buildings = varchar("buildings", 256)
    val crops = varchar("crops", 256)
    val waterSources = varchar("water_sources", 256)
    val soilType = varchar("soil_type", 256)
    val equipment = varchar("equipment", 256)
}




