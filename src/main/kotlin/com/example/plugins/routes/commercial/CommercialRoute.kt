package com.example.plugins.routes.commercial

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.properties.CommercialProperty
import com.example.plugins.routes.commercial.delete.image.deleteCommercialImage
import com.example.plugins.routes.commercial.delete.property.deleteCommercialProperty
import com.example.plugins.routes.commercial.delete.video.deleteCommercialVideo
import com.example.plugins.routes.commercial.get.getCommercialRoute
import com.example.plugins.routes.commercial.post.postCommercialRoute
import com.example.plugins.routes.commercial.put.putCommercialRoute
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


fun Route.createCommercialRoute() {
    route("/commercial") {
        postCommercialRoute()
        putCommercialRoute()
        getCommercialRoute()
        deleteCommercialImage()
        deleteCommercialVideo()
        deleteCommercialProperty()
    }
}