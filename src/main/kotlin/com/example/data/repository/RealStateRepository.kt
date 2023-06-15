package com.example.data.repository

import com.example.data.Database
import com.example.model.RealState

class RealStateRepository {
    private val realStateCollection = Database.realStateCollection

    suspend fun getAll(): List<RealState> {
        return realStateCollection.find().toList()
    }

    suspend fun add(realState: RealState): RealState {
        realStateCollection.insertOne(realState)
        return realState
    }
}
