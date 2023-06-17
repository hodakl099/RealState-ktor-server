package com.example.dao


import com.example.model.*
import com.example.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class PropertyDaoImpl : PropertyDao {

    private fun resultRowToResidentialProperty(row : ResultRow) = ResidentialProperty(
        property=row[ResidentialProperty.propery],
        propertyType =row[ResidentialProperties.propertyType],
        description =row[RealStates.description],
        latitude = row[RealStates.latitude],
        longitude = row[RealStates.longitude],
        images = emptyList(),
        videos = emptyList()
    )


    private fun toResidentialProperty(row: ResultRow): ResidentialProperty =
        ResidentialProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price]
            ),
            propertyType = row[ResidentialProperties.propertyType],
            squareFootage = row[ResidentialProperties.squareFootage],
            bedrooms = row[ResidentialProperties.bedrooms],
            bathrooms = row[ResidentialProperties.bathrooms],
            amenities = row[ResidentialProperties.amenities],
            parking = row[ResidentialProperties.parking],
            location = row[ResidentialProperties.location]
        )

    private fun toAgriculturalProperty(row: ResultRow): AgriculturalProperty =
        AgriculturalProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price]
            ),
            propertyType = row[AgriculturalProperties.propertyType],
            acres = row[AgriculturalProperties.acres],
            buildings = row[AgriculturalProperties.buildings],
            crops = row[AgriculturalProperties.crops],
            waterSources = row[AgriculturalProperties.waterSources],
            equipment = row[AgriculturalProperties.equipment],
            soilType = row[AgriculturalProperties.soilType]
        )

    override suspend fun addResidentialProperty(residentialProperty: ResidentialProperty): ResidentialProperty  = dbQuery{
            val propertyId = Properties.insert {
                it[agentContact] = residentialProperty.property.agentContact
                it[price] = residentialProperty.property.price
            } get Properties.id
            ResidentialProperties.insert {
                it[id] = propertyId
                it[propertyType] = residentialProperty.propertyType
                it[squareFootage] = residentialProperty.squareFootage
                it[bedrooms] = residentialProperty.bedrooms
                it[bathrooms] = residentialProperty.bathrooms
                it[amenities] = residentialProperty.amenities
                it[parking] = residentialProperty.parking
                it[location] = residentialProperty.location
            }
            residentialProperty
    }

    override suspend fun getResidentialProperty(id: Int): ResidentialProperty?  =  dbQuery{
        ResidentialProperties
            .select {
                ResidentialProperties.id eq id
            }
            .mapNotNull(::toResidentialProperty)
            .singleOrNull()
    }

    override suspend fun deleteResidentialProperty(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addAgriculturalProperty(agriculturalProperty: AgriculturalProperty): AgriculturalProperty = dbQuery{
        val propertyId = Properties.insert {
            it[agentContact] = agriculturalProperty.property.agentContact
            it[price] = agriculturalProperty.property.price
        } get Properties.id
        AgriculturalProperties.insert {
            it[id] = propertyId
            it[acres] = agriculturalProperty.acres
            it[propertyType] = agriculturalProperty.propertyType
            it[buildings] = agriculturalProperty.buildings
            it[crops]  = agriculturalProperty.crops
            it[waterSources] = agriculturalProperty.waterSources
            it[soilType] = agriculturalProperty.soilType
            it[equipment] = agriculturalProperty.equipment
        }
        agriculturalProperty
    }

    override suspend fun getAgriculturalProperty(id: Int): AgriculturalProperty? = dbQuery{
        AgriculturalProperties
            .select {
                AgriculturalProperties.id eq id
            }
            .mapNotNull(::toAgriculturalProperty)
            .singleOrNull()
    }

    override suspend fun deleteAgriculturalProperty(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAllResidentialProperties(): List<ResidentialProperty> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllAgriculturalProperties(): List<AgriculturalProperty> {
        TODO("Not yet implemented")
    }

}