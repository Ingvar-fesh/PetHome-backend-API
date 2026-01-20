package fr.epita.pethome.datamodel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "places")
@Data
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean isValidated = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_type_id", nullable = false)
    private PlaceType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    public Place(String name, String address, PlaceType type, Location location) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.location = location;
    }
}