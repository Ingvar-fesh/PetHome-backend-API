package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.Place;
import fr.epita.pethome.datamodel.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    List<Place> findByIsValidatedTrue();
    List<Place> findByType_NameAndIsValidatedTrue(String typeName);
}
