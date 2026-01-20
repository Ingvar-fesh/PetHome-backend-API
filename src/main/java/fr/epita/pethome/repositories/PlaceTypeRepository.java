package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceTypeRepository extends JpaRepository<PlaceType, Integer> {
}
