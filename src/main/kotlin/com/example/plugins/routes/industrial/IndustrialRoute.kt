package com.example.plugins.routes.industrial


import com.example.plugins.routes.industrial.delete.image.deleteIndustrialImage
import com.example.plugins.routes.industrial.delete.property.deleteIndustrialProperty
import com.example.plugins.routes.industrial.delete.video.deleteIndustrialVideo
import com.example.plugins.routes.industrial.get.getAllIndustrialProperty
import com.example.plugins.routes.industrial.get.getIndustrialProperty
import com.example.plugins.routes.industrial.post.postIndustrialProperty
import com.example.plugins.routes.industrial.put.putIndustrialProperty
import io.ktor.server.routing.*



fun Route.createIndustrialRoute() {
    route("/industrial") {
        postIndustrialProperty()
        putIndustrialProperty()
        getIndustrialProperty()
        getAllIndustrialProperty()
        deleteIndustrialProperty()
        deleteIndustrialImage()
        deleteIndustrialVideo()
    }
}