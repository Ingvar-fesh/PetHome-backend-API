package fr.epita.pethome.controllers;

import fr.epita.pethome.datamodel.Comment;
import fr.epita.pethome.datamodel.dto.CommentRequest;
import fr.epita.pethome.datamodel.dto.CommentResponseDTO;
import fr.epita.pethome.services.CommentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> addCommentToPost(@PathVariable("postId") Integer postId,
                                              @RequestBody CommentRequest request,
                                              Principal principal) {
        return ResponseEntity.ok(
                commentsService.addComment(postId, request.getText(), principal.getName())
        );
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDTO> getComments(@PathVariable("postId") Integer postId) {
        return commentsService.getCommentsForPost(postId);
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable("commentId") Integer commentId,
                                           Principal principal) {
        try {
            commentsService.deleteComment(commentId, principal.getName());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}