package com.example.petsrego.model

data class PetModel(
        val name: String,
        val type: String = "n/a",
        val breed: String = "n/a",
        val picture: List<String> = ArrayList(),
        val owner: String)