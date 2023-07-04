package com.example.dao

import kotlinx.coroutines.runBlocking


/**
 * Logging interceptor.
 */
val dao : PropertyDaoImpl = PropertyDaoImpl().apply {
    runBlocking {
        if (getAllResidentialProperties().isNotEmpty()) {
            println(getAllResidentialProperties())
        }
    }
}