package com.example.dao


import com.example.model.*
import com.example.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class PropertyDaoImpl : PropertyDao {


    private fun toResidentialProperty(row: ResultRow): ResidentialProperty =
        ResidentialProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price],
                images = Images.select { Images.propertyId eq row[Properties.id] }.map { it[Images.url] },
                videos = Videos.select { Videos.propertyId eq row[Properties.id] }.map { it[Videos.url] },
                location = row[Properties.location]
            ),
            propertyType = row[ResidentialProperties.propertyType],
            squareFootage = row[ResidentialProperties.squareFootage],
            bedrooms = row[ResidentialProperties.bedrooms],
            bathrooms = row[ResidentialProperties.bathrooms],
            amenities = row[ResidentialProperties.amenities],
            parking = row[ResidentialProperties.parking],
        )

    private fun toAgriculturalProperty(row: ResultRow): AgriculturalProperty =
        AgriculturalProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price],
                images = Images.select { Images.propertyId eq row[Properties.id] }.map { it[Images.url] },
                videos = Videos.select { Videos.propertyId eq row[Properties.id] }.map { it[Videos.url] },
                location = row[Properties.location]
            ),
            propertyType = row[AgriculturalProperties.propertyType],
            acres = row[AgriculturalProperties.acres],
            buildings = row[AgriculturalProperties.buildings],
            crops = row[AgriculturalProperties.crops],
            waterSources = row[AgriculturalProperties.waterSources],
            equipment = row[AgriculturalProperties.equipment],
            soilType = row[AgriculturalProperties.soilType]
        )

    override suspend fun addResidentialProperty(
        residentialProperty: ResidentialProperty,
        videoURL: List<String>,
        imageURL: List<String>
    ): ResidentialProperty = dbQuery {
        val idProperty = Properties.insert {
            it[agentContact] = residentialProperty.property.agentContact
            it[price] = residentialProperty.property.price
            it[location] = residentialProperty.property.location
        } get Properties.id
        ResidentialProperties.insert {
            it[id] = idProperty
            it[propertyType] = residentialProperty.propertyType
            it[squareFootage] = residentialProperty.squareFootage
            it[bedrooms] = residentialProperty.bedrooms
            it[bathrooms] = residentialProperty.bathrooms
            it[amenities] = residentialProperty.amenities
            it[parking] = residentialProperty.parking

        }
        videoURL.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = idProperty
                it[url] = imageUrl
            }
        }
        imageURL.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = idProperty
                it[url] = videoUrl
            }
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

    override suspend fun addAgriculturalProperty(
        agriculturalProperty: AgriculturalProperty,
        videoUrls: List<String>,
        imageUrls: List<String>
    ): AgriculturalProperty = dbQuery{
        val idProperty = Properties.insert {
            it[agentContact] = agriculturalProperty.property.agentContact
            it[price] = agriculturalProperty.property.price
        } get Properties.id
        AgriculturalProperties.insert {
            it[id] = idProperty
            it[acres] = agriculturalProperty.acres
            it[propertyType] = agriculturalProperty.propertyType
            it[buildings] = agriculturalProperty.buildings
            it[crops]  = agriculturalProperty.crops
            it[waterSources] = agriculturalProperty.waterSources
            it[soilType] = agriculturalProperty.soilType
            it[equipment] = agriculturalProperty.equipment
        }

        agriculturalProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = idProperty
                it[url] = videoUrl
            }
        }
        agriculturalProperty.property.images.forEach {imageUrl ->
        Images.insert {
            it[propertyId] = idProperty
            it[url] = imageUrl
        }

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

    override suspend fun getAllResidentialProperties(): List<ResidentialProperty>  = dbQuery{
            (Properties innerJoin ResidentialProperties)
                .selectAll()
                .map { toResidentialProperty(it) }
    }

    override suspend fun getAllAgriculturalProperties(): List<AgriculturalProperty>  = dbQuery{
            (Properties innerJoin AgriculturalProperties)
                .selectAll()
                .map { toAgriculturalProperty(it) }
    }
}