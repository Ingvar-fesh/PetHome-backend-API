package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType, Integer> {
}
