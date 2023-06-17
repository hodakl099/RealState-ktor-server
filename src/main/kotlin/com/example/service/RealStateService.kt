package com.example.service

import com.example.dao.PropertyDao
import com.example.model.RealState

class RealStateService(private val dao: PropertyDao) {
//
//    suspend fun getAllRealStates(): List<RealState> {
//        return dao.allRealStates()
//    }
//
//    suspend fun getRealState(id: Int): RealState? {
//        return dao.realState(id)
//    }
//
//    suspend fun addNewRealState(
//        title: String,
//        description: String,
//        latitude: Double,
//        longitude: Double,
//        videoURL: List<String>,
//        imageURL: List<String>
//    ): RealState? {
//        return dao.addNewRealState(title, description, latitude, longitude, videoURL, imageURL)
//    }
//
//    suspend fun editRealState(
//        id: Int,
//        title: String,
//        description: String,
//        latitude: Double,
//        longitude: Double,
//        videoURL: List<String>,
//        imageURL: List<String>
//    ): Boolean {
//        return dao.editRealState(id, title, description, latitude, longitude, videoURL, imageURL)
//    }
//
//    suspend fun deleteRealState(id: Int): Boolean {
//        return dao.deleteRealState(id)
//    }
}