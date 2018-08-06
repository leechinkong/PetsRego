package com.example.petsrego.controller;

import com.example.petsrego.model.Pet;
import com.example.petsrego.repository.PetRepository;
import com.google.common.collect.Lists;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.core.GcpProjectIdProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PetsRegoController {

  private final PetRepository petRepo;
  private final ApplicationContext context;
  private final GcpProjectIdProvider projectIdProvider;

  @Autowired
  public PetsRegoController(final PetRepository petRepo,
      final ApplicationContext context,
      final GcpProjectIdProvider projectIdProvider) {

    this.petRepo = petRepo;
    this.context = context;
    this.projectIdProvider = projectIdProvider;
  }

  @GetMapping("/pets")
  public List<Pet> getPets() {

    return Lists.newArrayList(petRepo.findAll());
  }

  @GetMapping("/pets/{id}/picture")
  public ResponseEntity<Resource> getPetPicture(@PathVariable String id) throws Exception {

    Pet pet = petRepo.findById(id).orElseThrow(() -> new Exception("Resource not found."));

    String gsBucket = "gs://" + projectIdProvider.getProjectId();
    Resource image = context.getResource(gsBucket + "/" + pet.getPicture());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    return new ResponseEntity<>(image, headers, HttpStatus.OK);
  }

  @PostMapping("/pets")
  public Pet addPet(@RequestBody Pet pet) {

    return petRepo.save(pet);
  }

  @PostMapping("/pets/{id}/picture")
  public Pet addPetPicture(@PathVariable String id, @RequestBody MultipartFile picture)
      throws Exception {

    Pet pet = petRepo.findById(id).orElseThrow(() -> new Exception("Resource not found."));
    if (picture != null && !picture.isEmpty() && "image/jpeg".equals(picture.getContentType())) {
      String gsBucket = "gs://" + projectIdProvider.getProjectId();
      String pictureLink = UUID.randomUUID().toString() + ".jpg";
      WritableResource resource = (WritableResource) context
          .getResource(gsBucket + "/" + pictureLink);

      OutputStream outputStream = resource.getOutputStream();
      outputStream.write(picture.getBytes());

      pet.setPicture(pictureLink);
      return petRepo.save(pet);
    }
    return null;
  }
}
