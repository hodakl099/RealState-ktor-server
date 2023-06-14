package com.example.data.dao

import com.example.model.RealState

interface DAOFacade {
    suspend fun allArticles() : List<RealState>
    suspend fun article(id : Int) : RealState?
    suspend fun addNewArticle(title : String, body : String) : RealState?
    suspend fun editArticle(id:Int, title: String,body: String) : Boolean
    suspend fun deleteArticle(id: Int) : Boolean


}