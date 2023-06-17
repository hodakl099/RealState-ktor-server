package com.example.util

import kotlinx.serialization.Serializable

@Serializable
data class BasicApiResponse(
    val success : Boolean = false,
    val message : String
)
