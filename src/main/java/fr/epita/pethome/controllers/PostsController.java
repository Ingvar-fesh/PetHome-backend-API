package fr.epita.pethome.controllers;

import fr.epita.pethome.datamodel.Post;
import fr.epita.pethome.datamodel.dto.PostRequest;
import fr.epita.pethome.datamodel.dto.PostResponseDTO;
import fr.epita.pethome.services.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request, Principal principal) {
        return ResponseEntity.ok(postsService.createPost(request, principal.getName()));
    }

    @GetMapping
    public List<PostResponseDTO> getAllPosts() {
        return postsService.getAllPosts();
    }

    @GetMapping("/user/{id}")
    public List<PostResponseDTO> getPostsByUser(@PathVariable("id") Integer usernameId) {
        return postsService.getPostsByAuthorId(usernameId);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Integer id,
                                        @RequestBody PostRequest request,
                                        Principal principal) {
        try {
            Post updatedPost = postsService.updatePost(id, request, principal.getName());
            return ResponseEntity.ok(updatedPost);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Integer id,
                                        Principal principal) {
        try {
            postsService.deletePost(id, principal.getName());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}