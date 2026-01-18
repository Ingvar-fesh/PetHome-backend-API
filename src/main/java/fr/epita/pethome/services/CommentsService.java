package fr.epita.pethome.services;

import fr.epita.pethome.datamodel.Comment;
import fr.epita.pethome.datamodel.Post;
import fr.epita.pethome.datamodel.User;
import fr.epita.pethome.datamodel.dto.CommentResponseDTO;
import fr.epita.pethome.repositories.CommentsRepository;
import fr.epita.pethome.repositories.PostsRepository;
import fr.epita.pethome.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

    public CommentsService(CommentsRepository commentsRepository,
                           PostsRepository postsRepository,
                           UsersRepository usersRepository) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
    }

    public Comment addComment(Integer postId, String text, String username) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setPost(post);
        comment.setAuthor(user);

        return commentsRepository.save(comment);
    }

    public List<CommentResponseDTO> getCommentsForPost(Integer postId) {
        List<Comment> comments = commentsRepository.findByPostId(postId);

        // Convert the list of Entities to a list of DTOs
        return comments.stream()
                .map(comment -> new CommentResponseDTO(
                        comment.getId(),
                        comment.getText(),
                        comment.getAuthor().getUsername(), // Just get the username!
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public void deleteComment(Integer commentId, String username) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // SECURITY: Allow delete if you are the COMMENT author OR the POST owner
        boolean isCommentAuthor = comment.getAuthor().getUsername().equals(username);
        boolean isPostOwner = comment.getPost().getAuthor().getUsername().equals(username);

        if (!isCommentAuthor && !isPostOwner) {
            throw new RuntimeException("You are not authorized to delete this comment");
        }

        commentsRepository.delete(comment);
    }
}