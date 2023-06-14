package com.example.data.dao

import com.example.model.RealState
import com.example.model.RealStates
import com.example.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToRealState(row : ResultRow) = RealState(
        id=row[RealStates.id],
        title =row[RealStates.title],
        description =row[RealStates.description],
        imageURL = row[RealStates.imageURL],
        videoURL = row[RealStates.videoURL],
        latitude = row[RealStates.latitude],
        longitude = row[RealStates.longitude]
    )
    override suspend fun allRealStates(): List<RealState> = dbQuery {
        RealStates.selectAll().map(::resultRowToRealState)
    }

    override suspend fun realState(id: Int): RealState?  = dbQuery {
        RealStates
            .select {
                RealStates.id eq id
            }
            .map(::resultRowToRealState)
            .singleOrNull()
    }

    override suspend fun addNewRealState(
        title: String,
        description: String,
        latitude: Double,
        longitude: Double,
        videoURL: String,
        imageURL: String
    ): RealState? = dbQuery {
        val insertStatement = RealStates.insert {
            it[RealStates.title] = title
            it[RealStates.description] = description
            it[RealStates.imageURL] = imageURL
            it[RealStates.videoURL] = videoURL
            it[RealStates.longitude] = longitude
            it[RealStates.latitude] = latitude
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToRealState)
    }

    override suspend fun editRealState(
        id: Int,
        title: String,
        description: String,
        latitude: Double,
        longitude: Double,
        videoURL: String,
        imageURL: String
    ): Boolean = dbQuery {
        RealStates.update({ RealStates.id eq id }) {
            it[RealStates.title] = title
            it[RealStates.description] = description
            it[RealStates.latitude] = latitude
            it[RealStates.longitude] = longitude
            it[RealStates.videoURL] = videoURL
            it[RealStates.imageURL] = imageURL
        } > 0
    }



    override suspend fun deleteRealState(id: Int): Boolean = dbQuery {
        RealStates.deleteWhere{
            RealStates.id eq id
        } > 0
    }
}