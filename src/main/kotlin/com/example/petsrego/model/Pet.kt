package com.example.petsrego.model

import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table
import java.util.*

@Table(name = "pet")
data class Pet(
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),
        var name: String? = null,
        var type: String? = null,
        var breed: String? = null,
        var picture: String? = null,
        var owner: String? = null)