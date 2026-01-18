package fr.epita.pethome.repositories;

import fr.epita.pethome.datamodel.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {
    Optional<CommentLike> findByCommentIdAndUserId(Integer commentId, Integer userId);
    long countByCommentId(Integer commentId);
}
