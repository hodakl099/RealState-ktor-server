package com.example.model.properties

import com.example.model.Properties
import com.example.model.Property
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * Residential Property.
 */
@Serializable
data class ResidentialProperty(
    val property: Property,
    val propertyType: String,
    val acres: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val amenities: String,
    val parking: String,
)

object ResidentialProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val propertyType = varchar("property_type", 256)
    val acres = integer("acres")
    val bedrooms = integer("bedrooms")
    val bathrooms = integer("bathrooms")
    val amenities = varchar("amenities", 256)
    val parking = varchar("parking",265)
}