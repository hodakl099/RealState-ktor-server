package com.example.model

import org.jetbrains.exposed.sql.Table

data class RealState(
    val id : Int,
    val title : String,
    val description : String,
    val latitude : Double,
    val longitude : Double,
    val videoURL : String,
    val imageURL : String
)


