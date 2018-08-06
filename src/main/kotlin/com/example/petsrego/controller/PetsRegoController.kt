package com.example.petsrego.controller

import com.example.petsrego.model.Pet
import com.example.petsrego.model.PetRequest
import com.example.petsrego.repository.PetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gcp.core.GcpProjectIdProvider
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.core.io.WritableResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.OutputStream
import java.util.*


@RestController
class PetsRegoController @Autowired constructor(private val petRepo: PetRepository) {

    @Autowired
    lateinit var context: ApplicationContext

    @Autowired
    lateinit var projectIdProvider: GcpProjectIdProvider

    @GetMapping("/pets")
    fun getPets() = petRepo.findAll()

    @GetMapping("/pets/{id}/picture")
    fun getPetPicture(@PathVariable id: String): ResponseEntity<Resource> {
        val pet: Pet? = petRepo.findById(id).orElse(null)
        val gsBucket: String = "gs://" + projectIdProvider.projectId
        val image: Resource? = context.getResource(gsBucket + "/" + pet?.picture)

        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_JPEG
        return ResponseEntity(image, headers, HttpStatus.OK)
    }

    @PostMapping("/pets")
    fun addPet(@RequestBody pet: PetRequest) {

        petRepo.save(Pet(name = pet.name,
                type = pet.type,
                breed = pet.breed,
                picture = uploadPicture(pet.picture),
                owner = pet.owner))
    }

    fun uploadPicture(picture: MultipartFile?): String? {

        var pictureLink: String? = null;
        if (picture != null && !picture.isEmpty && picture.contentType == "image/jpeg") {
            val gsBucket: String = "gs://" + projectIdProvider.projectId;

            pictureLink = UUID.randomUUID().toString() + ".jpg";
            val resource: WritableResource =
                    context.getResource(gsBucket + "/" + pictureLink) as WritableResource

            val outputStream: OutputStream = resource.outputStream
            outputStream.write(picture.bytes);
        }
        return pictureLink
    }
}