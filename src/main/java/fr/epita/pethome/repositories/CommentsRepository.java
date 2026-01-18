package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(Integer postId);
}
