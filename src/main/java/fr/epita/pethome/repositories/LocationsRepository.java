package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsRepository extends JpaRepository<Location, Integer> {
}
