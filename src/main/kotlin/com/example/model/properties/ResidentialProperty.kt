package com.example.model

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

data class ResidentialProperty(
    val property: Property,
    val propertyType: String,
    val squareFootage: Double,
    val bedrooms: Int,
    val bathrooms: Int,
    val amenities: String,
    val parking: Boolean,
)

object ResidentialProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val propertyType = varchar("property_type", 256)
    val squareFootage = double("square_footage")
    val bedrooms = integer("bedrooms")
    val bathrooms = integer("bathrooms")
    val amenities = varchar("amenities", 256)
    val parking = bool("parking")
}