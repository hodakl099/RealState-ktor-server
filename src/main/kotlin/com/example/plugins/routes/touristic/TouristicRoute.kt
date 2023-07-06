package com.example.plugins.routes.touristic

import com.example.plugins.routes.touristic.delete.image.deleteTouristicImage
import com.example.plugins.routes.touristic.delete.property.deleteTouristicProperty
import com.example.plugins.routes.touristic.delete.video.deleteTouristicVideo
import com.example.plugins.routes.touristic.get.getAllTouristicProperty
import com.example.plugins.routes.touristic.get.getTouristicProperty
import com.example.plugins.routes.touristic.post.postTouristicProperty
import com.example.plugins.routes.touristic.put.putTouristicProperty
import io.ktor.server.routing.*


fun Route.createTouristicRoute() {
    route("touristic") {
        postTouristicProperty()
        putTouristicProperty()
        deleteTouristicImage()
        getTouristicProperty()
        getAllTouristicProperty()
        deleteTouristicImage()
        deleteTouristicVideo()
        deleteTouristicProperty()
    }
}