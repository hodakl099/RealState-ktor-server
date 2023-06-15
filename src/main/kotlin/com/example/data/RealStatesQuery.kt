package com.example.data

import com.example.model.RealState

class RealStateQueries(private val database: Database) {
    suspend fun realStates(): List<RealState> {
        return database.realStateCollection.find().toList()
    }
}
