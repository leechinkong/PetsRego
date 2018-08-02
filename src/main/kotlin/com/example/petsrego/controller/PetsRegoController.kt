package com.example.petsrego.controller

import com.example.petsrego.model.Pet
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {

    @GetMapping("/pets")
    fun getPets() = Pet("ALly", "Beagle", "Lee")

    @PostMapping("/pets")
    fun addPet(@RequestBody pet: Pet) = pet
}