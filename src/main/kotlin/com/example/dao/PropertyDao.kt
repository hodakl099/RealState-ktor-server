package com.example.dao


import com.example.model.properties.ResidentialProperty
import com.example.model.properties.*


interface PropertyDao {
    /**
     * ResidentialProperty Dao.
     */
    suspend fun addResidentialProperty(residentialProperty: ResidentialProperty, videoURL : List<String>, imageURL : List<String>): ResidentialProperty

    suspend fun deleteResidentialProperty(id: Int): Boolean

    suspend fun getAllResidentialProperties(): List<ResidentialProperty>
    suspend fun getResidentialProperty(id: Int): ResidentialProperty?
    suspend fun updateResidentialProperty(id: Int,residentialProperty: ResidentialProperty): Boolean

    /**
     * AgriculturalProperty Dao.
     */
    suspend fun addAgriculturalProperty(agriculturalProperty: AgriculturalProperty, videoURL : List<String>, imageURL : List<String>): AgriculturalProperty
    suspend fun getAgriculturalProperty(id: Int): AgriculturalProperty?
    suspend fun deleteAgriculturalProperty(id: Int): Boolean

    suspend fun getAllAgriculturalProperties(): List<AgriculturalProperty>

    /**
     * OfficeProperty Dao.
     */
    suspend fun addOfficeProperty(residentialProperty: OfficeProperty, videoURL : List<String>, imageURL : List<String>): OfficeProperty

    suspend fun deleteOfficeProperty(id: Int): Boolean

    suspend fun getAllOfficeProperties(): List<OfficeProperty>
    suspend fun getOfficeProperty(id: Int): OfficeProperty?

    /**
     * IndustrialProperty Dao.
     */
    suspend fun addIndustrialProperty(residentialProperty: IndustrialProperty, videoURL : List<String>, imageURL : List<String>): IndustrialProperty

    suspend fun deleteIndustrialProperty(id: Int): Boolean

    suspend fun getAllIndustrialProperties(): List<IndustrialProperty>
    suspend fun getIndustrialProperty(id: Int): IndustrialProperty?

    /**
     * Commercial Property Dao.
     */
    suspend fun addCommercialProperty(commercialProperty: CommercialProperty, videoURL : List<String>, imageURL : List<String>): CommercialProperty

    suspend fun deleteCommercialProperty(id: Int): Boolean

    suspend fun getAllCommercialProperties(): List<CommercialProperty>
    suspend fun getCommercialProperty(id: Int): CommercialProperty?


    /**
     * leisure and touristic Property Dao.
     */
    suspend fun addTouristicProperty(leisureAndTouristicProperty: LeisureAndTouristicProperty, videoURL : List<String>, imageURL : List<String>): LeisureAndTouristicProperty

    suspend fun deleteTouristicProperty(id: Int): Boolean

    suspend fun getAllTouristicProperties(): List<LeisureAndTouristicProperty>
    suspend fun getTouristicProperty(id: Int): LeisureAndTouristicProperty?
}