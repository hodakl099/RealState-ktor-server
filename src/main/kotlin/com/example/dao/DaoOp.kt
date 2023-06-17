package com.example.dao

import kotlinx.coroutines.runBlocking

val dao : PropertyDaoImpl = PropertyDaoImpl().apply {
    runBlocking {
//        if (allRealStates().isNotEmpty()) {
//            addNewRealState("Jetpack compose", "jetpack compose its going to be great investment.",0.0,0.0,
//                mutableListOf("1","2","3"),
//                mutableListOf("1","2","3"))
//        }

    }
}