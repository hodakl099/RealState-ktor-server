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

    override suspend fun updateResidentialProperty(id: Int, updatedProperty: ResidentialProperty): Boolean = dbQuery {
        val updateProperty = Properties.update( { Properties.id eq id  }) {
            it[agentContact] = updatedProperty.property.agentContact
            it[price] = updatedProperty.property.price
            it[location] = updatedProperty.property.location
        }
        val updateResidential = ResidentialProperties.update({ ResidentialProperties.id eq id }){
            it[propertyType] = updatedProperty.propertyType
            it[squareFootage] = updatedProperty.squareFootage
            it[bedrooms] = updatedProperty.bedrooms
            it[bathrooms] = updatedProperty.bathrooms
            it[amenities] = updatedProperty.amenities
            it[parking] = updatedProperty.parking
        }
        updatedProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = id
                it[url] = imageUrl
            }
        }
        updatedProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = id
                it[url] = videoUrl
            }
        }
        updateResidential > 0 && updateProperty > 0
    }
    override suspend fun getResidentialProperty(id: Int): ResidentialProperty?  =  dbQuery{
        (Properties innerJoin ResidentialProperties)
            .select { Properties.id eq id }
            .map { toResidentialProperty(it) }
            .firstOrNull()
    }

    override suspend fun deleteResidentialProperty(id: Int): Boolean  = dbQuery {
        val deleteImages = Images.deleteWhere { propertyId eq id  }
        val deleteVideos = Videos.deleteWhere { propertyId eq id }
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

    override suspend fun updateAgriculturalProperty(id: Int, agriculturalProperty: AgriculturalProperty): Boolean =  dbQuery {
        val updateProperty = Properties.update( { Properties.id eq id  }) {
            it[agentContact] = agriculturalProperty.property.agentContact
            it[price] = agriculturalProperty.property.price
            it[location] = agriculturalProperty.property.location
        }
        val updateAgriculturalProperty = AgriculturalProperties.update({ AgriculturalProperties.id eq id }){
            it[propertyType] = agriculturalProperty.propertyType
            it[acres] = agriculturalProperty.acres
            it[buildings] = agriculturalProperty.buildings
            it[crops] = agriculturalProperty.crops
            it[waterSources] = agriculturalProperty.waterSources
            it[soilType] = agriculturalProperty.soilType
            it[equipment] = agriculturalProperty.equipment
        }
        agriculturalProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = id
                it[url] = imageUrl
            }
        }
        agriculturalProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = id
                it[url] = videoUrl
            }
        }
        updateAgriculturalProperty > 0 && updateProperty > 0
    }

    override suspend fun getAgriculturalProperty(id: Int): AgriculturalProperty? = dbQuery{
        (Properties innerJoin AgriculturalProperties)
            .select { Properties.id eq id }
            .map { toAgriculturalProperty(it) }
            .firstOrNull()
    }

    override suspend fun deleteAgriculturalProperty(id: Int): Boolean = dbQuery{
        val deleteImages  = Images.deleteWhere { Images.propertyId eq id }
        val deleteVideos = Videos.deleteWhere { Videos.propertyId eq id  }
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

    override suspend fun updateOfficeProperty(id: Int, officeProperty: OfficeProperty): Boolean =  dbQuery {
        val updateProperty = Properties.update( { Properties.id eq id  }) {
            it[agentContact] = officeProperty.property.agentContact
            it[price] = officeProperty.property.price
            it[location] = officeProperty.property.location
        }
        val updateOfficeProperty = OfficeProperties.update({ OfficeProperties.id eq id }){
            it[layoutType] = officeProperty.layoutType
            it[squareFoot] = officeProperty.squareFoot
            it[floorNumber] = officeProperty.floorNumber
            it[amenities] = officeProperty.amenities
            it[accessibility] = officeProperty.accessibility
        }
        officeProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = id
                it[url] = imageUrl
            }
        }
        officeProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = id
                it[url] = videoUrl
            }
        }
        updateOfficeProperty > 0 && updateProperty > 0
    }

    override suspend fun deleteOfficeProperty(id: Int): Boolean = dbQuery {
        val deleteImages  = Images.deleteWhere { Images.propertyId eq id }
        val deleteVideos = Videos.deleteWhere { Videos.propertyId eq id  }
        val deleteOfficeProperty = OfficeProperties.deleteWhere { OfficeProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
        deleteOfficeProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllOfficeProperties(): List<OfficeProperty> = dbQuery{
        (Properties innerJoin OfficeProperties)
            .selectAll()
            .map { toOfficeProperty(it) }
    }

    override suspend fun getOfficeProperty(id: Int): OfficeProperty?= dbQuery {
        (Properties innerJoin OfficeProperties)
            .select { Properties.id eq id }
            .map { toOfficeProperty(it) }
            .firstOrNull()
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
    override suspend fun updateIndustrialProperty(id: Int, updatedProperty: IndustrialProperty): Boolean = dbQuery {
        val updateProperty = Properties.update( { Properties.id eq id  }) {
            it[agentContact] = updatedProperty.property.agentContact
            it[price] = updatedProperty.property.price
            it[location] = updatedProperty.property.location
        }
        val updateResidential = IndustrialProperties.update({ IndustrialProperties.id eq id }){
            it[propertyType] = updatedProperty.propertyType
            it[squareFoot] = updatedProperty.squareFoot
            it[zoningInfo] = updatedProperty.zoningInfo
            it[cellingHeight]  = updatedProperty.cellingHeight
            it[numberOfLoadingDocks] = updatedProperty.numberOfLoadingDocks
            it[powerCapabilities] = updatedProperty.powerCapabilities
            it[accessToTransportation] = updatedProperty.accessToTransportation
            it[environmentalReports] = updatedProperty.environmentalReports
        }
        updatedProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = id
                it[url] = imageUrl
            }
        }
        updatedProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = id
                it[url] = videoUrl
            }
        }
        updateResidential > 0 && updateProperty > 0
    }

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
        val deleteImages  = Images.deleteWhere { propertyId eq id }
        val deleteVideos = Videos.deleteWhere { propertyId eq id  }
        val deleteIndustrialProperty = IndustrialProperties.deleteWhere { IndustrialProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
         deleteIndustrialProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllIndustrialProperties(): List<IndustrialProperty> = dbQuery{
        (Properties innerJoin IndustrialProperties)
            .selectAll()
            .map { toIndustrialProperty(it) }
    }

    override suspend fun getIndustrialProperty(id: Int): IndustrialProperty? = dbQuery {
        (Properties innerJoin IndustrialProperties)
            .select { Properties.id eq id }
            .map { toIndustrialProperty(it) }
            .firstOrNull()
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

    override suspend fun updateCommercialProperty(id: Int, commercialProperty: CommercialProperty): Boolean = dbQuery {
        val updateProperty = Properties.update( { Properties.id eq id  }) {
            it[agentContact] = commercialProperty.property.agentContact
            it[price] = commercialProperty.property.price
            it[location] = commercialProperty.property.location
        }
        val updateCommercial = CommercialProperties.update({ CommercialProperties.id eq id }){
            it[propertyType] = commercialProperty.propertyType
            it[squareFoot] = commercialProperty.squareFoot
            it[zoningInfo] = commercialProperty.zoningInfo
            it[trafficCount]  = commercialProperty.trafficCount
            it[amenities] = commercialProperty.amenities
        }
        commercialProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = id
                it[url] = imageUrl
            }
        }
        commercialProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = id
                it[url] = videoUrl
            }
        }
        updateCommercial > 0 && updateProperty > 0
    }

    override suspend fun deleteCommercialProperty(id: Int): Boolean = dbQuery  {
        val deleteImages  = Images.deleteWhere { Images.propertyId eq id }
        val deleteVideos = Videos.deleteWhere { Videos.propertyId eq id  }
        val deleteCommercialProperty = CommercialProperties.deleteWhere { CommercialProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
         deleteCommercialProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllCommercialProperties(): List<CommercialProperty> = dbQuery{
        (Properties innerJoin CommercialProperties)
            .selectAll()
            .map { toCommercialProperty(it) }
    }

    override suspend fun getCommercialProperty(id: Int): CommercialProperty?  = dbQuery{
        (Properties innerJoin CommercialProperties)
            .select { Properties.id eq id }
            .map { toCommercialProperty(it) }
            .firstOrNull()
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

    override suspend fun updateTouristicProperty(id: Int, touristicProperty: LeisureAndTouristicProperty): Boolean = dbQuery {
        val updateProperty = Properties.update( { Properties.id eq id  }) {
            it[agentContact] = touristicProperty.property.agentContact
            it[price] = touristicProperty.property.price
            it[location] = touristicProperty.property.location
        }
        val updateCommercial = LeisureAndTouristicProperties.update({ LeisureAndTouristicProperties.id eq id }){
            it[propertyType] = touristicProperty.propertyType
            it[squareFoot] = touristicProperty.squareFoot
            it[rooms] = touristicProperty.rooms
            it[units]  = touristicProperty.units
            it[amenities] = touristicProperty.amenities
            it[proximityToAttractions] = touristicProperty.proximityToAttractions
            it[occupancyRate] = touristicProperty.occupancyRate
        }
        touristicProperty.property.images.forEach { imageUrl ->
            Images.insert {
                it[propertyId] = id
                it[url] = imageUrl
            }
        }
        touristicProperty.property.videos.forEach { videoUrl ->
            Videos.insert {
                it[propertyId] = id
                it[url] = videoUrl
            }
        }
        updateCommercial > 0 && updateProperty > 0
    }


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
        val deleteImages  = Images.deleteWhere { Images.propertyId eq id }
        val deleteVideos = Videos.deleteWhere { Videos.propertyId eq id  }
        val deleteTouristicProperty = LeisureAndTouristicProperties.deleteWhere { LeisureAndTouristicProperties.id eq id}
        val deleteProperty  =  Properties.deleteWhere { Properties.id  eq id }
        deleteTouristicProperty > 0 && deleteProperty > 0 && deleteImages > 0 && deleteVideos > 0
    }

    override suspend fun getAllTouristicProperties(): List<LeisureAndTouristicProperty> = dbQuery {
        (Properties innerJoin LeisureAndTouristicProperties)
            .selectAll()
            .map { toTouristicProperty(it) }
    }

    override suspend fun getTouristicProperty(id: Int): LeisureAndTouristicProperty? = dbQuery {
        (Properties innerJoin LeisureAndTouristicProperties)
            .select { Properties.id eq id }
            .map { toTouristicProperty(it) }
            .firstOrNull()
    }

    override suspend fun deleteVideosById(videoId: Int): Boolean = dbQuery  {
        Videos.deleteWhere { Videos.id eq videoId } > 0
    }

    override suspend fun deleteImageByPropertyId(imageId: Int): Boolean = dbQuery {
        Images.deleteWhere { Images.id eq imageId } > 0
    }


}