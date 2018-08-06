package com.example.petsrego.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table
import java.util.*

@Table(name = "pet")
data class Pet(
        @JsonIgnore
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),
        var name: String? = null,
        var type: String = "n/a",
        var breed: String = "n/a",
        var picture: String = "n/a",
        var owner: String? = null) {
}