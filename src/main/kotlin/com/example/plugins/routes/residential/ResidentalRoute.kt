package com.example.plugins.routes.residential

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.Videos
import com.example.model.properties.ResidentialProperty
import com.example.plugins.routes.residential.delete.image.deleteResidentialImage
import com.example.plugins.routes.residential.delete.property.deleteResidentialProperty
import com.example.plugins.routes.residential.delete.video.deleteResidentialVideo
import com.example.plugins.routes.residential.get.getResidentialProperty
import com.example.plugins.routes.residential.post.postResidentialProperty
import com.example.plugins.routes.residential.put.putResidentialProperty
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
import org.litote.kmongo.util.idValue
import java.io.FileInputStream

fun Route.createResidentialRoute() {
    route("/residential") {
        postResidentialProperty()
        putResidentialProperty()
        getResidentialProperty()
        deleteResidentialImage()
        deleteResidentialVideo()
        deleteResidentialProperty()
    }
}

