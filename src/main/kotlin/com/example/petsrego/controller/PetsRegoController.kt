package com.example.petsrego.controller

import com.example.petsrego.model.Pet
import com.example.petsrego.model.PetModel
import com.example.petsrego.repository.PetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PetsRegoController @Autowired constructor(private val petRepo: PetRepository) {

    @GetMapping("/pets")
    fun getPets(): List<PetModel> {
        val pets: MutableList<PetModel> = mutableListOf()
        petRepo.findAll().forEach {
            pets.add(PetModel(it.name.orEmpty(),
                    it.type.orEmpty(),
                    it.breed.orEmpty(),
                    it.picture.orEmpty().split("\n"),
                    it.owner.orEmpty()))
        }
        return pets
    }

    @PostMapping("/pets")
    fun addPet(@RequestBody pet: PetModel) {
        petRepo.save(Pet(name = pet.name,
                type = pet.type,
                breed = pet.breed,
                picture = pet.picture.joinToString("\n"),
                owner = pet.owner))
    }
}