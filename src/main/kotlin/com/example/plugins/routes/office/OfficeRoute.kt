package com.example.plugins.routes.office


import com.example.plugins.routes.office.delete.image.deleteOfficeImage
import com.example.plugins.routes.office.delete.property.deleteOfficeProperty
import com.example.plugins.routes.office.delete.video.deleteOfficeVideo
import com.example.plugins.routes.office.get.getAllOfficeProperty
import com.example.plugins.routes.office.get.getOfficeProperty
import com.example.plugins.routes.office.post.postOfficeProperty
import com.example.plugins.routes.office.put.putOfficeProperty
import io.ktor.server.routing.*

fun Route.createOfficeRoute() {
    route("/office") {
        postOfficeProperty()
        putOfficeProperty()
        getOfficeProperty()
        getAllOfficeProperty()
        deleteOfficeProperty()
        deleteOfficeImage()
        deleteOfficeVideo()
    }

}