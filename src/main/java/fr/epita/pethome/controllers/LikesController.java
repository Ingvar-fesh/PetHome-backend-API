package fr.epita.pethome.controllers;

import fr.epita.pethome.services.LikesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LikesController {

    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable("postId") Integer postId, Principal principal) {
        String status = likesService.togglePostLike(postId, principal.getName());
        return ResponseEntity.ok(Map.of("status", status));
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<?> likeComment(@PathVariable("commentId") Integer commentId, Principal principal) {
        String status = likesService.toggleCommentLike(commentId, principal.getName());
        return ResponseEntity.ok(Map.of("status", status));
    }
}