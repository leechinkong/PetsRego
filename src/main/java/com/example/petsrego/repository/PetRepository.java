package com.example.petsrego.repository;

import com.example.petsrego.model.Pet;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PetRepository extends PagingAndSortingRepository<Pet, String> {}