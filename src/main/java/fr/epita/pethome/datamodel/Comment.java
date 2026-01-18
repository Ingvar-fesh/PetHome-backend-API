package fr.epita.pethome.datamodel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Link to the User who wrote the comment
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    // Link to the Post being commented on
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}