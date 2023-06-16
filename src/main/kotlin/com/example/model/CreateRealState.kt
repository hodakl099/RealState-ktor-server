package com.example.model

data class CreateRealState(
    val title : String,
    val description : String,
    val videoURL : String?,
    val imageURL : String?,
    val latitude: Double?,
    val longitude: Double?,
)
