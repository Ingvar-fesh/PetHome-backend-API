package fr.epita.pethome.datamodel.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostResponseDTO {
    private Integer id;
    private String title;
    private String body;
    private String imageUrl;
    private String topicName;
    private String authorName;
    private LocalDateTime createdAt;
    private long likesCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostResponseDTO(Integer id, String title, String body, String imageUrl,
                           String topicName, String authorName, LocalDateTime createdAt,
                           long likesCount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.topicName = topicName;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
    }
}