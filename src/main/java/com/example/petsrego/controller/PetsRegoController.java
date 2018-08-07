package com.example.petsrego.controller;

import com.example.petsrego.model.Pet;
import com.example.petsrego.model.PetModel;
import com.example.petsrego.repository.PetRepository;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetsRegoController {

  private final PetRepository petRepo;

  @Autowired
  public PetsRegoController(final PetRepository petRepo) {

    this.petRepo = petRepo;
  }

  @GetMapping("/pets")
  public List<PetModel> getPets() {

    List<PetModel> models = new ArrayList<>();
    petRepo.findAll().forEach(pet -> {
          models.add(toPetModel(pet));
        }
    );
    return models;
  }

  @PostMapping("/pets")
  public PetModel addPet(@RequestBody PetModel model) {

    Pet pet = new Pet();
    pet.setName(model.getName());
    pet.setType(model.getType());
    pet.setBreed(model.getBreed());
    pet.setOwner(model.getOwner());
    List<String> multiLinePicture = model.getPicture();
    if (!CollectionUtils.isEmpty(multiLinePicture)) {
      pet.setPicture(multiLinePicture.stream().collect(Collectors.joining("\n")));
    }
    return toPetModel(petRepo.save(pet));
  }

  private PetModel toPetModel(Pet pet) {
    PetModel model = new PetModel();
    model.setId(pet.getId());
    model.setName(pet.getName());
    model.setType(pet.getType());
    model.setBreed(pet.getBreed());
    model.setOwner(pet.getOwner());
    String singleLinePicture = pet.getPicture();
    if (!StringUtils.isEmpty(singleLinePicture)) {
      List<String> lines = Lists.newArrayList(singleLinePicture.split("\n"));
      lines.add(0, "================");
      lines.add("================");
      model.setPicture(lines);
    }
    return model;
  }
}
