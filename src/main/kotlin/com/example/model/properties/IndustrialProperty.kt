package com.example.model.properties

import com.example.model.Properties
import com.example.model.Property
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


/**
 * Industrial Property.
 */
@Serializable
data class IndustrialProperty(
    val property: Property,
    val propertyType: String,
    val acres : Int,
    val zoningInfo : String,
    val cellingHeight : Int,
    val numberOfLoadingDocks : Int,
    val powerCapabilities: String,
    val accessToTransportation : String,
    val environmentalReports : String,
)

object IndustrialProperties : Table() {
    val id = integer("property_id").references(Properties.id)
    val propertyType = varchar("property_type", 256)
    val acres = integer("acres")
    val zoningInfo = varchar("zoningInfo", 256)
    val cellingHeight = integer("cellingHeight")
    val numberOfLoadingDocks = integer("numberOfLoadingDocks")
    val powerCapabilities = varchar("powerCapabilities", 1024)
    val accessToTransportation = varchar("accessToTransportation", 1024)
    val environmentalReports = varchar("environmentalReports", 1024)
}




