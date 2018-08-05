package com.example.petsrego.controller

import com.example.petsrego.model.Pet
import com.example.petsrego.repository.PetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PetsRegoController @Autowired constructor(private val petRepo: PetRepository) {

    @GetMapping("/pets")
    fun getPets() = petRepo.findAll()

    @PostMapping("/pets")
    fun addPet(@RequestBody pet: Pet) = petRepo.save(pet)
}