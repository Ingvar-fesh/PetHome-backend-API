package fr.epita.pethome.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id // <--- Every entity MUST have an ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
}