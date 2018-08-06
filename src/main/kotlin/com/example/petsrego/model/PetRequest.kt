package com.example.petsrego.model

import org.springframework.web.multipart.MultipartFile

data class PetRequest(
        val name: String,
        val type: String = "n/a",
        val breed: String = "n/a",
        val picture: MultipartFile? = null,
        val owner: String)