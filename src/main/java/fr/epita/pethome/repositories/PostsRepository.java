package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Integer> {
    List<Post> findByTopicId(Integer topicId);
    List<Post> findByAuthorId(Integer usernameId);
}
