package com.example.plugins.routes.residential

import com.example.plugins.routes.residential.delete.image.deleteResidentialImage
import com.example.plugins.routes.residential.delete.property.deleteResidentialProperty
import com.example.plugins.routes.residential.delete.video.deleteResidentialVideo
import com.example.plugins.routes.residential.get.getAllResidentialProperty
import com.example.plugins.routes.residential.get.getResidentialProperty
import com.example.plugins.routes.residential.post.postResidentialProperty
import com.example.plugins.routes.residential.put.putResidentialProperty
import io.ktor.server.routing.*


fun Route.createResidentialRoute() {
    route("/residential") {
        postResidentialProperty()
        putResidentialProperty()
        getResidentialProperty()
        getAllResidentialProperty()
        deleteResidentialImage()
        deleteResidentialVideo()
        deleteResidentialProperty()
    }
}

