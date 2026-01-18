package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByPostIdAndUserId(Integer postId, Integer userId);

    Integer countByPostId(Integer postId);
}
