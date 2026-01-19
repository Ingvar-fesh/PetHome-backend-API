package fr.epita.pethome.services;

import fr.epita.pethome.datamodel.Pet;
import fr.epita.pethome.datamodel.PetType;
import fr.epita.pethome.datamodel.User;
import fr.epita.pethome.datamodel.dto.PetRequest;
import fr.epita.pethome.datamodel.dto.PetResponseDTO;
import fr.epita.pethome.repositories.PetRepository;
import fr.epita.pethome.repositories.PetTypeRepository;
import fr.epita.pethome.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final UsersRepository usersRepository;

    public PetService(PetRepository petRepository,
                      PetTypeRepository petTypeRepository,
                      UsersRepository usersRepository) {
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.usersRepository = usersRepository;
    }

    public Pet createPet(PetRequest request, String username) {
        User owner = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PetType petType = petTypeRepository.findById(request.getPetTypeId())
                .orElseThrow(() -> new RuntimeException("Pet Type not found"));

        Pet pet = new Pet(request.getName(), request.getImageUrl(), owner, petType);
        return petRepository.save(pet);
    }

    public List<PetResponseDTO> getPetsByOwnerId(Integer ownerId) {
        return petRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PetResponseDTO getPetById(Integer petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        return convertToDTO(pet);
    }

    public void deletePet(Integer petId, String username) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        if (!pet.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to delete this pet");
        }

        petRepository.delete(pet);
    }

    private PetResponseDTO convertToDTO(Pet pet) {
        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getImageUrl(),
                pet.getPetType().getName(),
                pet.getOwner().getId(),
                pet.getOwner().getUsername()
        );
    }
}
