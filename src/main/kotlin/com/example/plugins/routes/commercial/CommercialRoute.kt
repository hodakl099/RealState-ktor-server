package com.example.plugins.routes.commercial

import com.example.plugins.routes.commercial.delete.image.deleteCommercialImage
import com.example.plugins.routes.commercial.delete.property.deleteCommercialProperty
import com.example.plugins.routes.commercial.delete.video.deleteCommercialVideo
import com.example.plugins.routes.commercial.get.getAllCommercialRoute
import com.example.plugins.routes.commercial.get.getCommercialRoute
import com.example.plugins.routes.commercial.post.postCommercialRoute
import com.example.plugins.routes.commercial.put.putCommercialRoute
import io.ktor.server.routing.*



fun Route.createCommercialRoute() {
    route("/commercial") {
        postCommercialRoute()
        putCommercialRoute()
        getCommercialRoute()
        getAllCommercialRoute()
        deleteCommercialImage()
        deleteCommercialVideo()
        deleteCommercialProperty()
    }
}