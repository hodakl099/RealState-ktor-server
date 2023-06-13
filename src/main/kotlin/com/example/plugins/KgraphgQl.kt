package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.apurebase.kgraphql.KGraphQL
import com.example.model.Dummy
import com.example.model.RealState
import io.ktor.http.cio.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ktor.graphql.GraphQLRequest
import java.util.*

private val realStates = listOf(
    RealState("image1.jpg", "1000 sqft", "Location 1"),
    RealState("image2.jpg", "2000 sqft", "Location 2"),
    RealState("image1.jpg", "3000 sqft", "Location 3")
)
private val Dummy = listOf(
    Dummy("Mahmoud"),
    Dummy("Marwan"),
    Dummy("Thanaa")
)
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

                        property("image") {
                            description = "The image of the property"
                            resolver { realState : RealState -> realState.image }
                        }

                        property("realStateSize") {
                            description = "The size of the property"
                            resolver { realState : RealState -> realState.realStateSize }
                        }

                        property("location") {
                            description = "The location of the property"
                            resolver { realState : RealState -> realState.location }
                        }
                    }


            type<Dummy> {
                description = "a list of dummy shit"

                property("name") {
                    description = "The name of the property"
                    resolver { dummy: Dummy -> dummy.name  }
                }
            }

                    query("properties") {
                        description = "Return all properties"
                        resolver { -> realStates }
                    }


            query("dummy") {
                description = "return the list of dummies"
                resolver { -> Dummy }
            }
                }
    }
}