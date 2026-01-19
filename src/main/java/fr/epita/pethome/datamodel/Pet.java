package fr.epita.pethome.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    //A Pet belongs to one User (owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) //Maps to ownerId in the diagram
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User owner;

    //A Pet has one PetType
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_type_id", nullable = false) //Maps to petTypeId in the diagram
    private PetType petType;

    public Pet(String name, String imageUrl, User owner, PetType petType) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.owner = owner;
        this.petType = petType;
    }
}