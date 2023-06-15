package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.data.repository.RealStateRepository
import com.example.model.RealState
import com.example.resolver.Mutation
import graphql.schema.idl.SchemaParser
import io.ktor.http.cio.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

private val realStates = listOf(
    RealState(0, "1000 sqft", "Location 1",0.0,0.0,"videoUrl","imageUrl"),
    RealState(0, "1000 sqft", "Location 1",0.0,0.0,"videoUrl","imageUrl"),
    RealState(0, "1000 sqft", "Location 1",0.0,0.0,"videoUrl","imageUrl"),
)

fun Application.configureGraphQl() {
    val schemaParser = SchemaParser()
    val schema = schemaParser.parse(
        this::class.java.getResource("schema.graphql")?.readText() ?: return
    )
    val realStateRepository = RealStateRepository()
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


            mutation("addProperty") {
                resolver { realState: RealState -> realStateRepository.add(realState) }
            }
                    query("properties") {
                        description = "Return all properties"
                        resolver { -> realStates }
                    }


        }
    }
}
