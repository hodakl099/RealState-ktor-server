package com.example.util

import com.example.data.models.Images
import com.example.data.models.RealStates
import com.example.data.models.Videos
import com.example.model.AgriculturalProperties
import com.example.model.ResidentialProperties
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
       val database =  Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "mahmoud99"
        )

        transaction(database) {
            SchemaUtils.create(AgriculturalProperties,ResidentialProperties,Videos,Images)
        }
    }

    suspend fun <T> dbQuery(block : suspend () -> T) : T =
        newSuspendedTransaction(Dispatchers.IO) { block() }


}