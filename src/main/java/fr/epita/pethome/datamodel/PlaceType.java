package fr.epita.pethome.datamodel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "place_types")
@Data
@NoArgsConstructor
public class PlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public PlaceType(String name) {
        this.name = name;
    }
}
