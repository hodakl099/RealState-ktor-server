package com.example.plugins.routes.agricultural

import com.example.plugins.routes.agricultural.delete.image.deleteAgriculturalImage
import com.example.plugins.routes.agricultural.delete.property.deleteAgriculturalProperty
import com.example.plugins.routes.agricultural.delete.video.deleteAgriculturalVideo
import com.example.plugins.routes.agricultural.get.getAgriculturalRoute
import com.example.plugins.routes.agricultural.get.getAllAgriculturalRoute
import com.example.plugins.routes.agricultural.post.postAgriculturalRoute
import com.example.plugins.routes.agricultural.put.putAgriculturalRoute
import io.ktor.server.routing.*


fun Route.createAgriculturalRoute() {
    route("/agricultural") {
        postAgriculturalRoute()
        getAgriculturalRoute()
        putAgriculturalRoute()
        deleteAgriculturalProperty()
        deleteAgriculturalImage()
        deleteAgriculturalVideo()
        getAllAgriculturalRoute()
    }
}