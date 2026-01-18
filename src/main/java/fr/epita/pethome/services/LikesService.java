package fr.epita.pethome.services;

import fr.epita.pethome.datamodel.*;
import fr.epita.pethome.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikesService {

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;

    public LikesService(PostLikeRepository postLikeRepository, CommentLikeRepository commentLikeRepository,
                        PostsRepository postsRepository, CommentsRepository commentsRepository, UsersRepository usersRepository) {
        this.postLikeRepository = postLikeRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.postsRepository = postsRepository;
        this.commentsRepository = commentsRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public String togglePostLike(Integer postId, String username) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId());

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            return "Unliked";
        } else {
            Post post = postsRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            postLikeRepository.save(new PostLike(user, post));
            return "Liked";
        }
    }

    @Transactional
    public String toggleCommentLike(Integer commentId, String username) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentIdAndUserId(commentId, user.getId());

        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            return "Unliked";
        } else {
            Comment comment = commentsRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            commentLikeRepository.save(new CommentLike(user, comment));
            return "Liked";
        }
    }
}