package com.example.data.dao

import com.example.data.models.*
import com.example.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToRealState(row : ResultRow) = RealState(
        id=row[RealStates.id],
        title =row[RealStates.title],
        description =row[RealStates.description],
        latitude = row[RealStates.latitude],
        longitude = row[RealStates.longitude],
        images = emptyList(),
        videos = emptyList()
    )

    private fun resultRowToImage(row : ResultRow) = Image(
        id = row[Images.id],
        realStateId = row[Images.realStateId],
        url = row[Images.url]
    )
    private fun resultRowToVideo(row: ResultRow) = Video(
        id = row[Videos.id],
        realStateId = row[Videos.realStateId],
        url = row[Videos.url]
    )
    override suspend fun allRealStates(): List<RealState> = dbQuery {
        val realStates = RealStates.selectAll().map(::resultRowToRealState).toMutableList()
        val images = Images.selectAll().map(::resultRowToImage)
        val videos = Videos.selectAll().map(::resultRowToVideo)
        realStates.forEach { realState ->
            realState.images = images.filter { it.realStateId == realState.id }
            realState.videos = videos.filter { it.realStateId == realState.id }
        }
        realStates
    }


    override suspend fun realState(id: Int): RealState?  = dbQuery {
        RealStates
            .select {
                RealStates.id eq id
            }
            .mapNotNull(::resultRowToRealState)
            .singleOrNull()
    }

    override suspend fun addNewRealState(
        title: String,
        description: String,
        latitude: Double,
        longitude: Double,
        videoURL: List<String>,
        imageURL: List<String>
    ): RealState? = dbQuery {
        val realStateId = RealStates.insert {
            it[RealStates.title] = title
            it[RealStates.description] = description
            it[RealStates.longitude] = longitude
            it[RealStates.latitude] = latitude
        } get RealStates.id

        Videos.batchInsert(videoURL) { video ->
            this[Videos.realStateId] = realStateId
            this[Videos.url] = video
        }
        Images.batchInsert(imageURL) { image ->
            this[Images.realStateId] = realStateId
            this[Images.url] = image
        }


        RealStates.select { RealStates.id eq realStateId }.singleOrNull()?.let(::resultRowToRealState)
    }

    override suspend fun editRealState(
        id: Int,
        title: String,
        description: String,
        latitude: Double,
        longitude: Double,
        videoURL: List<String>,
        imageURL: List<String>
    ): Boolean = dbQuery {
        RealStates.update({ RealStates.id eq id }) {
            it[RealStates.title] = title
            it[RealStates.description] = description
            it[RealStates.latitude] = latitude
            it[RealStates.longitude] = longitude
        } > 0
    }




    override suspend fun deleteRealState(id: Int): Boolean = dbQuery {
        RealStates.deleteWhere{
            RealStates.id eq id
        } > 0
    }
}