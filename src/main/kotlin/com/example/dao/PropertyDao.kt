package com.example.dao


import com.example.model.properties.AgriculturalProperty
import com.example.model.properties.ResidentialProperty

interface PropertyDao {
    suspend fun addResidentialProperty(residentialProperty: ResidentialProperty, videoURL : List<String>, imageURL : List<String>): ResidentialProperty
    suspend fun getResidentialProperty(id: Int): ResidentialProperty?
    suspend fun deleteResidentialProperty(id: Int): Boolean
    suspend fun addAgriculturalProperty(agriculturalProperty: AgriculturalProperty, videoURL : List<String>, imageURL : List<String>): AgriculturalProperty
    suspend fun getAgriculturalProperty(id: Int): AgriculturalProperty?
    suspend fun deleteAgriculturalProperty(id: Int): Boolean
    suspend fun getAllResidentialProperties(): List<ResidentialProperty>
    suspend fun getAllAgriculturalProperties(): List<AgriculturalProperty>

}