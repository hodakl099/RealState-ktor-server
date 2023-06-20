package com.example.dao


import com.example.model.Image
import com.example.model.Video
import com.example.model.properties.ResidentialProperty
import com.example.model.properties.*



interface PropertyDao {
    /**
     * ResidentialProperty Dao.
     */
    suspend fun addResidentialProperty(residentialProperty: ResidentialProperty, videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): ResidentialProperty

    suspend fun deleteResidentialProperty(id: Int): Boolean

    suspend fun getAllResidentialProperties(): List<ResidentialProperty>
    suspend fun getResidentialProperty(id: Int): ResidentialProperty?
    suspend fun updateResidentialProperty(id: Int,residentialProperty: ResidentialProperty,videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): Boolean

    /**
     * AgriculturalProperty Dao.
     */
    suspend fun addAgriculturalProperty(agriculturalProperty: AgriculturalProperty, videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): AgriculturalProperty
    suspend fun getAgriculturalProperty(id: Int): AgriculturalProperty?
    suspend fun deleteAgriculturalProperty(id: Int): Boolean

    suspend fun getAllAgriculturalProperties(): List<AgriculturalProperty>

    suspend fun updateAgriculturalProperty(id: Int,agriculturalProperty: AgriculturalProperty,videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): Boolean

    /**
     * OfficeProperty Dao.
     */
    suspend fun addOfficeProperty(residentialProperty: OfficeProperty, videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): OfficeProperty

    suspend fun deleteOfficeProperty(id: Int): Boolean

    suspend fun getAllOfficeProperties(): List<OfficeProperty>
    suspend fun getOfficeProperty(id: Int): OfficeProperty?
    suspend fun updateOfficeProperty(id: Int,officeProperty: OfficeProperty,videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): Boolean

    /**
     * IndustrialProperty Dao.
     */
    suspend fun addIndustrialProperty(residentialProperty: IndustrialProperty, videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): IndustrialProperty

    suspend fun deleteIndustrialProperty(id: Int): Boolean

    suspend fun getAllIndustrialProperties(): List<IndustrialProperty>
    suspend fun getIndustrialProperty(id: Int): IndustrialProperty?
    suspend fun updateIndustrialProperty(id: Int,industrialProperty: IndustrialProperty,videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): Boolean
    /**
     * Commercial Property Dao.
     */
    suspend fun addCommercialProperty(commercialProperty: CommercialProperty, videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): CommercialProperty

    suspend fun deleteCommercialProperty(id: Int): Boolean

    suspend fun getAllCommercialProperties(): List<CommercialProperty>
    suspend fun getCommercialProperty(id: Int): CommercialProperty?
    suspend fun updateCommercialProperty(id: Int,commercialProperty: CommercialProperty,videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): Boolean

    /**
     * leisure and touristic Property Dao.
     */
    suspend fun addTouristicProperty(leisureAndTouristicProperty: LeisureAndTouristicProperty, videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): LeisureAndTouristicProperty

    suspend fun deleteTouristicProperty(id: Int): Boolean

    suspend fun getAllTouristicProperties(): List<LeisureAndTouristicProperty>
    suspend fun getTouristicProperty(id: Int): LeisureAndTouristicProperty?
    suspend fun updateTouristicProperty(id: Int,touristicProperty: LeisureAndTouristicProperty,videoURL : List<Pair<String,String>>, imageURL : List<Pair<String,String>>): Boolean

    /**
     * Images And Videos operations *Delete and *Get.
     */

    suspend fun deleteVideosById(videoId: Int): Boolean

    suspend fun deleteImageByPropertyId(imageId: Int): Boolean

    suspend fun getImageById(imageId : Int) : Image?

    suspend fun getVideoById(videoId : Int) : Video?
}