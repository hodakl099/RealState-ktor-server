package com.example.util


import com.example.model.AgriculturalProperties
import com.example.model.Images
import com.example.model.ResidentialProperties
import com.example.model.Videos
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
            user = "alkateb",
            password = "weedinternal099$$#"
        )

        transaction(database) {
            SchemaUtils.create(AgriculturalProperties,ResidentialProperties, Videos, Images)
        }
    }

    suspend fun <T> dbQuery(block : suspend () -> T) : T =
        newSuspendedTransaction(Dispatchers.IO) { block() }


}