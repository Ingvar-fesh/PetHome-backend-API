package fr.epita.pethome.datamodel.dto;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    private Integer id;
    private String text;
    private String authorName;
    private LocalDateTime createdAt;

    public CommentResponseDTO(Integer id, String text, String authorName, LocalDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.authorName = authorName;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
