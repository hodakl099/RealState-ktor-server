package com.example.data.dao

import com.example.model.RealState
import com.example.model.RealStates
import com.example.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToArticle(row : ResultRow) = RealState(
        id=row[RealStates.id],
        title =row[RealStates.title],
        body =row[RealStates.body]
    )
    override suspend fun allArticles(): List<RealState> = dbQuery {
        RealStates.selectAll().map(::resultRowToArticle)
    }

    override suspend fun article(id: Int): RealState?  = dbQuery {
        RealStates
            .select {
                RealStates.id eq id
            }
            .map(::resultRowToArticle)
            .singleOrNull()
    }

    override suspend fun addNewArticle(title: String, body: String): RealState? = dbQuery {
        val insertStatement = RealStates.insert {
            it[RealStates.title] = title
            it[RealStates.body] = body
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToArticle)
    }

    override suspend fun editArticle(id: Int, title: String, body: String): Boolean = dbQuery {
     RealStates.update({ RealStates.id eq id }) {
         it[RealStates.title] = title
         it[RealStates.body] = body
     } > 0
    }

    override suspend fun deleteArticle(id: Int): Boolean = dbQuery {
        RealStates.deleteWhere{
            RealStates.id eq id
        } > 0
    }
}