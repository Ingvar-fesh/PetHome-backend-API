package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwnerId(Integer ownerId);
}
