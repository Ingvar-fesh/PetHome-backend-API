package fr.epita.pethome.controllers;

import fr.epita.pethome.datamodel.Pet;
import fr.epita.pethome.datamodel.dto.PetRequest;
import fr.epita.pethome.datamodel.dto.PetResponseDTO;
import fr.epita.pethome.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetsController {

    private final PetService petService;

    public PetsController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody PetRequest request, Principal principal) {
        return ResponseEntity.ok(petService.createPet(request, principal.getName()));
    }

    @GetMapping("/owner/{id}")
    public List<PetResponseDTO> getPetsByOwner(@PathVariable("id") Integer ownerId) {
        return petService.getPetsByOwnerId(ownerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> getPet(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(petService.getPetById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Integer id, Principal principal) {
        try {
            petService.deletePet(id, principal.getName());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}