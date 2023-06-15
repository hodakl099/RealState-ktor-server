package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.apurebase.kgraphql.KGraphQL
import com.example.model.Dummy
import com.example.model.RealState
import graphql.schema.idl.SchemaParser
import io.ktor.http.cio.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ktor.graphql.GraphQLRequest
import java.util.*

private val realStates = listOf(
    RealState(0, "1000 sqft", "Location 1",0.0,0.0,"videoUrl","imageUrl"),
    RealState(0, "1000 sqft", "Location 1",0.0,0.0,"videoUrl","imageUrl"),
    RealState(0, "1000 sqft", "Location 1",0.0,0.0,"videoUrl","imageUrl"),
)
private val Dummy = listOf(
    Dummy("Mahmoud"),
    Dummy("Marwan"),
    Dummy("Thanaa")
)
fun Application.configureGraphQl() {
//    val schemaParser = SchemaParser()
//    val schema = schemaParser.parse(
//        this::class.java.getResource("schema.graphql")?.readText() ?: return
//        listOf(Mutation())
//    )
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
        schema {

        }
    }
}

/***
 * now I created the dashboard dummy to actually add data to the database from another app.
 * now i want to do that with GraphQl.
 */