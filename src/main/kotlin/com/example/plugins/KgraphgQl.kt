package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.data.dao
import com.example.model.RealState
import io.ktor.http.cio.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*


fun Application.configureGraphQl() {

    install(GraphQL) {
        playground = true
        schema {
                    stringScalar<UUID> {
                        serialize = { it.toString() }
                        deserialize = { UUID.fromString(it) }
                    }

                    type<RealState> {
                        description = "A real estate property"

                        property("imageURL") {
                            description = "The image of the property"
                            resolver { realState : RealState -> realState.imageURL }
                        }

                        property("description") {
                            description = "The size of the property"
                            resolver { realState : RealState -> realState.description }
                        }

                        property("videoURL") {
                            description = "The location of the property"
                            resolver { realState : RealState -> realState.videoURL }
                        }
                    }
                    query("properties") {
                        description = "Return all properties"
                        resolver { -> dao.allRealStates() }
                    }
        }
    }
}
