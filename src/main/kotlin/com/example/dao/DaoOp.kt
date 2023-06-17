package com.example.dao

import kotlinx.coroutines.runBlocking

val dao : PropertyDaoImpl = PropertyDaoImpl().apply {
    runBlocking {
        if (getAllResidentialProperties().isNotEmpty()) {
            println(getAllResidentialProperties())
        }

    }
}