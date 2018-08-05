package com.example.petsrego.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table
import java.util.UUID

@Table(name = "pet")
data class Pet(val name: String,
               val type: String = "n/a",
               val breed: String = "n/a",
               val picture: String = "n/a",
               val owner: String) {

    @JsonIgnore
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
}