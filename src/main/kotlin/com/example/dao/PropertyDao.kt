package com.example.dao

import com.example.data.models.RealState
import com.example.model.AgriculturalProperty
import com.example.model.ResidentialProperty

interface PropertyDao {
    suspend fun addResidentialProperty(residentialProperty: ResidentialProperty): ResidentialProperty
    suspend fun getResidentialProperty(id: Int): ResidentialProperty?
    suspend fun deleteResidentialProperty(id: Int): Boolean
    suspend fun addAgriculturalProperty(agriculturalProperty: AgriculturalProperty): AgriculturalProperty
    suspend fun getAgriculturalProperty(id: Int): AgriculturalProperty?
    suspend fun deleteAgriculturalProperty(id: Int): Boolean
    suspend fun getAllResidentialProperties(): List<ResidentialProperty>
    suspend fun getAllAgriculturalProperties(): List<AgriculturalProperty>

}