package com.example.data

import com.example.data.dao.DAOFacade
import com.example.data.dao.DAOFacadeImpl
import kotlinx.coroutines.runBlocking

val dao : DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if (allArticles().isEmpty()) {
            addNewArticle("Jetpack compose", "jetpack compose its going to be great investment.")
        }
    }
}