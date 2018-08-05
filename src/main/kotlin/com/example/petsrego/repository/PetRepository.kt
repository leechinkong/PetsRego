package com.example.petsrego.repository

import com.example.petsrego.model.Pet;
import org.springframework.data.repository.PagingAndSortingRepository;

interface PetRepository: PagingAndSortingRepository<Pet, String>