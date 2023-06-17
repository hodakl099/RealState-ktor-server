package com.example.model

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

data class ResidentialProperty(
    val property: Property,
    val propertyType: String,
    val squareFootage: BigDecimal,
    val bedrooms: Int,
    val bathrooms: Int,
    val amenities: String,
    val parking: Boolean,
    val location: String
)

object ResidentialProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val propertyType = varchar("property_type", 256)
    val squareFootage = decimal("square_footage", 12, 2)
    val bedrooms = integer("bedrooms")
    val bathrooms = integer("bathrooms")
    val amenities = varchar("amenities", 256)
    val parking = bool("parking")
    val location = varchar("location", 256)
}