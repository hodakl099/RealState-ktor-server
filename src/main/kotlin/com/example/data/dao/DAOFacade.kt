package com.example.data.dao

import com.example.data.models.RealState

interface DAOFacade {
    suspend fun allRealStates() : List<RealState>
    suspend fun realState(id : Int) : RealState?
    suspend fun addNewRealState(title : String, description : String,latitude: Double,longitude: Double,videoURL : List<String>,imageURL : List<String>) : RealState?
    suspend fun editRealState(id:Int, title : String, description : String,latitude: Double,longitude: Double,videoURL : List<String>,imageURL : List<String>) : Boolean
    suspend fun deleteRealState(id: Int) : Boolean


}