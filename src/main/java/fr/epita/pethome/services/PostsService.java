package fr.epita.pethome.services;

import fr.epita.pethome.datamodel.Post;
import fr.epita.pethome.datamodel.Topic;
import fr.epita.pethome.datamodel.User;
import fr.epita.pethome.datamodel.dto.PostRequest;
import fr.epita.pethome.repositories.PostsRepository;
import fr.epita.pethome.repositories.TopicsRepository;
import fr.epita.pethome.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final TopicsRepository topicsRepository;
    private final UsersRepository usersRepository;

    public PostsService(PostsRepository postsRepository,
                        TopicsRepository topicsRepository,
                        UsersRepository usersRepository) {
        this.postsRepository = postsRepository;
        this.topicsRepository = topicsRepository;
        this.usersRepository = usersRepository;
    }

    public Post createPost(PostRequest request, String username) {
        User author = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicsRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setImageUrl(request.getImageUrl());
        post.setAuthor(author);
        post.setTopic(topic);

        return postsRepository.save(post);
    }

    public List<Post> getPostsByAuthorId(Integer usernameId) {
        return postsRepository.findByAuthorId(usernameId);
    }

    public List<Post> getAllPosts() {
        return postsRepository.findAll();
    }


    public Post updatePost(Integer postId,
                           PostRequest request,
                           String currentUsername) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //Users can only modify their own posts
        if (!post.getAuthor().getUsername().equals(currentUsername)) {
            throw new RuntimeException("You are not authorized to update this post");
        }

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            post.setTitle(request.getTitle());
        }

        if (request.getBody() != null && !request.getBody().isEmpty()) {
            post.setBody(request.getBody());
        }

        if (request.getImageUrl() != null) {
            post.setImageUrl(request.getImageUrl());
        }

        if (request.getTopicId() != null) {
            Topic topic = topicsRepository.findById(request.getTopicId())
                    .orElseThrow(() -> new RuntimeException("Topic not found"));
            post.setTopic(topic);
        }

        return postsRepository.save(post);
    }


    public void deletePost(Integer postId, String currentUsername) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //Users can only modify their own posts
        if (!post.getAuthor().getUsername().equals(currentUsername)) {
            throw new RuntimeException("You are not authorized to delete this post");
        }

        postsRepository.delete(post);
    }
}
