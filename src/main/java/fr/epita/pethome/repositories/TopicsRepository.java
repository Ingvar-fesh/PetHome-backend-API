package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicsRepository extends JpaRepository<Topic, Integer> {
    Optional<Topic> findByName(String name);
}