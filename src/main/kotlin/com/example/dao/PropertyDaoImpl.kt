package com.example.dao


import com.example.model.*
import com.example.model.properties.*
import com.example.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PropertyDaoImpl : PropertyDao {

    /**
     * ResidentialProperty dao implementation.
     */
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

    override suspend fun deleteResidentialProperty(id: Int): Boolean  = dbQuery {
        val deleteImages = Images.deleteWhere { Images.id eq id  }
        val deleteVideos = Videos.deleteWhere { Videos.id eq id }
        val deleteResidentialProperty = ResidentialProperties.deleteWhere { ResidentialProperties.id eq  id }
        val deleteProperty =  Properties.deleteWhere { Properties.id eq id }
        deleteResidentialProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }
    override suspend fun getAllResidentialProperties(): List<ResidentialProperty>  = dbQuery{
        (Properties innerJoin ResidentialProperties)
            .selectAll()
            .map { toResidentialProperty(it) }
    }

    /**
     * AgriculturalProperty dao implementation.
     */

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

    override suspend fun addAgriculturalProperty(
        agriculturalProperty: AgriculturalProperty,
        videoURL: List<String>,
        imageURL: List<String>
    ): AgriculturalProperty = dbQuery{
        val idProperty = Properties.insert {
            it[agentContact] = agriculturalProperty.property.agentContact
            it[price] = agriculturalProperty.property.price
            it[location] = agriculturalProperty.property.location
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

    override suspend fun deleteAgriculturalProperty(id: Int): Boolean = dbQuery{
        val deleteImages  = Images.deleteWhere { Images.id eq id }
        val deleteVideos = Videos.deleteWhere { Videos.id eq id  }
        val deleteAgriculturalProperty = AgriculturalProperties.deleteWhere { AgriculturalProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
        deleteAgriculturalProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }


    override suspend fun getAllAgriculturalProperties(): List<AgriculturalProperty>  = dbQuery{
            (Properties innerJoin AgriculturalProperties)
                .selectAll()
                .map { toAgriculturalProperty(it) }
    }

    /**
     * OfficeProperty dao implementation.
     */

    private fun toOfficeProperty(row: ResultRow): OfficeProperty =
        OfficeProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price],
                images = Images.select { Images.propertyId eq row[Properties.id] }.map { it[Images.url] },
                videos = Videos.select { Videos.propertyId eq row[Properties.id] }.map { it[Videos.url] },
                location = row[Properties.location]
            ),
            layoutType = row[OfficeProperties.layoutType],
            squareFoot = row[OfficeProperties.squareFoot],
            floorNumber = row[OfficeProperties.floorNumber],
            amenities = row[OfficeProperties.amenities],
            accessibility = row[OfficeProperties.accessibility],
        )
    override suspend fun addOfficeProperty(
        officeProperty: OfficeProperty,
        videoURL: List<String>,
        imageURL: List<String>
    ): OfficeProperty  = dbQuery{
        val idProperty = Properties.insert {
            it[agentContact] = officeProperty.property.agentContact
            it[price] = officeProperty.property.price
            it[location] =officeProperty.property.location
        } get Properties.id
        OfficeProperties.insert {
            it[id] = idProperty
            it[layoutType] = officeProperty.layoutType
            it[squareFoot] = officeProperty.squareFoot
            it[floorNumber] = officeProperty.floorNumber
            it[amenities]  = officeProperty.amenities
            it[accessibility] = officeProperty.accessibility
        }

        officeProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = idProperty
                it[url] = videoUrl
            }
        }
        officeProperty.property.images.forEach {imageUrl ->
            Images.insert {
                it[propertyId] = idProperty
                it[url] = imageUrl
            }

        }
        officeProperty
    }

    override suspend fun deleteOfficeProperty(id: Int): Boolean = dbQuery {
        val deleteImages  = Images.deleteWhere { Images.id eq id }
        val deleteVideos = Videos.deleteWhere { Videos.id eq id  }
        val deleteOfficeProperty = OfficeProperties.deleteWhere { OfficeProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
        deleteOfficeProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllOfficeProperties(): List<OfficeProperty> = dbQuery{
        (Properties innerJoin OfficeProperties)
            .selectAll()
            .map { toOfficeProperty(it) }
    }

    override suspend fun getOfficeProperty(id: Int): OfficeProperty? {
        TODO("Not yet implemented")
    }

    /**
     * IndustrialProperty Dao Implementation.
     */
    private fun toIndustrialProperty(row: ResultRow): IndustrialProperty =
        IndustrialProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price],
                images = Images.select { Images.propertyId eq row[Properties.id] }.map { it[Images.url] },
                videos = Videos.select { Videos.propertyId eq row[Properties.id] }.map { it[Videos.url] },
                location = row[Properties.location]
            ),
            propertyType = row[IndustrialProperties.propertyType],
            squareFoot = row[IndustrialProperties.squareFoot],
            zoningInfo = row[IndustrialProperties.zoningInfo],
            cellingHeight = row[IndustrialProperties.cellingHeight],
            numberOfLoadingDocks = row[IndustrialProperties.numberOfLoadingDocks],
            powerCapabilities = row[IndustrialProperties.powerCapabilities],
            accessToTransportation = row[IndustrialProperties.accessToTransportation],
            environmentalReports = row[IndustrialProperties.environmentalReports],
        )

    override suspend fun addIndustrialProperty(
        industrialProperty: IndustrialProperty,
        videoURL: List<String>,
        imageURL: List<String>
    ): IndustrialProperty = dbQuery{
        val idProperty = Properties.insert {
            it[agentContact] = industrialProperty.property.agentContact
            it[price] = industrialProperty.property.price
            it[location] =industrialProperty.property.location
        } get Properties.id
        IndustrialProperties.insert {
            it[id] = idProperty
            it[propertyType] = industrialProperty.propertyType
            it[squareFoot] = industrialProperty.squareFoot
            it[zoningInfo] = industrialProperty.zoningInfo
            it[cellingHeight]  = industrialProperty.cellingHeight
            it[numberOfLoadingDocks] = industrialProperty.numberOfLoadingDocks
            it[powerCapabilities] = industrialProperty.powerCapabilities
            it[accessToTransportation] = industrialProperty.accessToTransportation
            it[environmentalReports] = industrialProperty.environmentalReports
        }

        industrialProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = idProperty
                it[url] = videoUrl
            }
        }
        industrialProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = idProperty
                it[url] = imageUrl
            }

        }
        industrialProperty
    }

    override suspend fun deleteIndustrialProperty(id: Int): Boolean  =  dbQuery{
        val deleteImages  = Images.deleteWhere { Images.id eq id }
        val deleteVideos = Videos.deleteWhere { Videos.id eq id  }
        val deleteIndustrialProperty = IndustrialProperties.deleteWhere { IndustrialProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
         deleteIndustrialProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllIndustrialProperties(): List<IndustrialProperty> = dbQuery{
        (Properties innerJoin IndustrialProperties)
            .selectAll()
            .map { toIndustrialProperty(it) }
    }

    override suspend fun getIndustrialProperty(id: Int): IndustrialProperty? {
        TODO("Not yet implemented")
    }


    /**
     * Commercial Property Dao Implementation.
     */


    private fun toCommercialProperty(row: ResultRow): CommercialProperty =
        CommercialProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price],
                images = Images.select { Images.propertyId eq row[Properties.id] }.map { it[Images.url] },
                videos = Videos.select { Videos.propertyId eq row[Properties.id] }.map { it[Videos.url] },
                location = row[Properties.location]
            ),
            propertyType = row[CommercialProperties.propertyType],
            squareFoot = row[CommercialProperties.squareFoot],
            zoningInfo = row[CommercialProperties.zoningInfo],
            trafficCount = row[CommercialProperties.trafficCount],
            amenities = row[CommercialProperties.amenities],
        )
    override suspend fun addCommercialProperty(
        commercialProperty: CommercialProperty,
        videoURL: List<String>,
        imageURL: List<String>
    ): CommercialProperty = dbQuery {
        val idProperty = Properties.insert {
            it[agentContact] = commercialProperty.property.agentContact
            it[price] = commercialProperty.property.price
            it[location] =commercialProperty.property.location
        } get Properties.id
        CommercialProperties.insert {
            it[id] = idProperty
            it[propertyType] = commercialProperty.propertyType
            it[squareFoot] = commercialProperty.squareFoot
            it[zoningInfo] = commercialProperty.zoningInfo
            it[trafficCount]  = commercialProperty.trafficCount
            it[amenities] = commercialProperty.amenities
        }

        commercialProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = idProperty
                it[url] = videoUrl
            }
        }
        commercialProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = idProperty
                it[url] = imageUrl
            }

        }
        commercialProperty
    }

    override suspend fun deleteCommercialProperty(id: Int): Boolean = dbQuery  {
        val deleteImages  = Images.deleteWhere { Images.id eq id }
        val deleteVideos = Videos.deleteWhere { Videos.id eq id  }
        val deleteCommercialProperty = CommercialProperties.deleteWhere { CommercialProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
         deleteCommercialProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllCommercialProperties(): List<CommercialProperty> = dbQuery{
        (Properties innerJoin CommercialProperties)
            .selectAll()
            .map { toCommercialProperty(it) }
    }

    override suspend fun getCommercialProperty(id: Int): CommercialProperty? {
        TODO("Not yet implemented")
    }

    /**
     * Leisure Property Dao Implementation.
     */
    private fun toTouristicProperty(row: ResultRow): LeisureAndTouristicProperty =
        LeisureAndTouristicProperty(
            property = Property(
                id = row[Properties.id],
                agentContact = row[Properties.agentContact],
                price = row[Properties.price],
                images = Images.select { Images.propertyId eq row[Properties.id] }.map { it[Images.url] },
                videos = Videos.select { Videos.propertyId eq row[Properties.id] }.map { it[Videos.url] },
                location = row[Properties.location]
            ),
            propertyType = row[LeisureAndTouristicProperties.propertyType],
            squareFoot = row[LeisureAndTouristicProperties.squareFoot],
            rooms = row[LeisureAndTouristicProperties.rooms],
            units = row[LeisureAndTouristicProperties.units],
            amenities = row[LeisureAndTouristicProperties.amenities],
            proximityToAttractions = row[LeisureAndTouristicProperties.proximityToAttractions],
            occupancyRate = row[LeisureAndTouristicProperties.occupancyRate],
        )

    override suspend fun addTouristicProperty(
        leisureAndTouristicProperty: LeisureAndTouristicProperty,
        videoURL: List<String>,
        imageURL: List<String>
    ): LeisureAndTouristicProperty  = dbQuery{
        val idProperty = Properties.insert {
            it[agentContact] = leisureAndTouristicProperty.property.agentContact
            it[price] = leisureAndTouristicProperty.property.price
            it[location] =leisureAndTouristicProperty.property.location
        } get Properties.id
        LeisureAndTouristicProperties.insert {
            it[id] = idProperty
            it[propertyType] = leisureAndTouristicProperty.propertyType
            it[squareFoot] = leisureAndTouristicProperty.squareFoot
            it[rooms] = leisureAndTouristicProperty.rooms
            it[units]  = leisureAndTouristicProperty.units
            it[amenities] = leisureAndTouristicProperty.amenities
            it[proximityToAttractions] = leisureAndTouristicProperty.proximityToAttractions
            it[occupancyRate] = leisureAndTouristicProperty.occupancyRate
        }

        leisureAndTouristicProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = idProperty
                it[url] = videoUrl
            }
        }
        leisureAndTouristicProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = idProperty
                it[url] = imageUrl
            }

        }
        leisureAndTouristicProperty

    }

    override suspend fun deleteTouristicProperty(id: Int): Boolean  = dbQuery{
        val deleteImages  = Images.deleteWhere { Images.id eq id }
        val deleteVideos = Videos.deleteWhere { Videos.id eq id  }
        val deleteTouristicProperty = LeisureAndTouristicProperties.deleteWhere { LeisureAndTouristicProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
        deleteTouristicProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllTouristicProperties(): List<LeisureAndTouristicProperty> = dbQuery {
        (Properties innerJoin LeisureAndTouristicProperties)
            .selectAll()
            .map { toTouristicProperty(it) }
    }

    override suspend fun getTouristicProperty(id: Int): LeisureAndTouristicProperty? {
        TODO("Not yet implemented")
    }

}