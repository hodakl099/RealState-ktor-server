package com.example.plugins.routes.industrial

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.properties.IndustrialProperty
import com.example.plugins.routes.industrial.delete.image.deleteIndustrialImage
import com.example.plugins.routes.industrial.delete.property.deleteIndustrialProperty
import com.example.plugins.routes.industrial.delete.video.deleteIndustrialVideo
import com.example.plugins.routes.industrial.get.getIndustrialProperty
import com.example.plugins.routes.industrial.post.postIndustrialProperty
import com.example.plugins.routes.industrial.put.putIndustrialProperty
import com.example.util.BasicApiResponse
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream


fun Route.createIndustrialRoute() {
    route("/industrial") {
        postIndustrialProperty()
        putIndustrialProperty()
        getIndustrialProperty()
        deleteIndustrialProperty()
        deleteIndustrialImage()
        deleteIndustrialVideo()
    }
}