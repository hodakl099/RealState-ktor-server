package com.example.plugins.routes.office

import com.example.dao.dao
import com.example.model.Image
import com.example.model.Property
import com.example.model.Video
import com.example.model.properties.OfficeProperty
import com.example.plugins.routes.office.delete.image.deleteOfficeImage
import com.example.plugins.routes.office.delete.property.deleteOfficeProperty
import com.example.plugins.routes.office.delete.video.deleteOfficeVideo
import com.example.plugins.routes.office.get.getOfficeProperty
import com.example.plugins.routes.office.post.postOfficeProperty
import com.example.plugins.routes.office.put.putOfficeProperty
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

fun Route.createOfficeRoute() {
    route("/office") {
        postOfficeProperty()
        putOfficeProperty()
        getOfficeProperty()
        deleteOfficeProperty()
        deleteOfficeImage()
        deleteOfficeVideo()
    }

}