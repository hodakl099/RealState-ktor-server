package com.example.plugins.routes.touristic

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.properties.LeisureAndTouristicProperty
import com.example.plugins.routes.touristic.delete.image.deleteTouristicImage
import com.example.plugins.routes.touristic.delete.property.deleteTouristicProperty
import com.example.plugins.routes.touristic.delete.video.deleteTouristicVideo
import com.example.plugins.routes.touristic.get.getTouristicProperty
import com.example.plugins.routes.touristic.post.postTouristicProperty
import com.example.plugins.routes.touristic.put.putTouristicProperty
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


fun Route.createTouristicRoute() {
    route("touristic") {
        postTouristicProperty()
        putTouristicProperty()
        deleteTouristicImage()
        getTouristicProperty()
        deleteTouristicImage()
        deleteTouristicVideo()
        deleteTouristicProperty()
    }
}