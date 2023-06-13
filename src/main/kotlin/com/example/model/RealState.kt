package com.example.model

import kotlinx.serialization.Serializable


@Serializable
data class RealState(
    val image : String?,
    val realStateSize : String,
    val location : String
)