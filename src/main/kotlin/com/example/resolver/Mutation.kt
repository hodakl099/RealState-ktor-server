package com.example.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.data.dao
import com.example.util.Response
import io.netty.handler.codec.http.multipart.FileUpload
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


class Mutation : GraphQLMutationResolver {

    suspend fun uploadData(
        title : String,
        description: String?,
        video: Upload?,
        image: Upload?,
        latitude: Double?,
        longitude: Double?
    ) : Response {
        val videoURL: String? = video?.let { writeUploadedFile(it, "video") }
        val imageURL: String? = image?.let { writeUploadedFile(it, "image") }


        dao.addNewRealState(
            title = title ,
            description = description ?: "",
            videoURL = videoURL ?: "",
            imageURL = imageURL ?: "",
            longitude = longitude ?: 0.0,
            latitude = latitude ?: 0.0
        )

            // Now newEntry is the ID of the new row in your table
            // You can create and return your Response object based on this


        // If the transaction was successful, return a success message
        // If the transaction failed, this code would not be reached, and an exception would be thrown
        return Response(true, "Data and files uploaded successfully")
    }

    private fun writeUploadedFile(file: Upload, fileType: String): String {
        val fileBytes = file.bytes
        val dirPath = "uploads"
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdir()
        }
        val filePath = "$dirPath/${file.originalFileName}"
        File(filePath).writeBytes(fileBytes)
        return filePath
    }
}

data class Upload(val bytes: ByteArray, val originalFileName: String)
data class Response(val success: Boolean, val message: String)

