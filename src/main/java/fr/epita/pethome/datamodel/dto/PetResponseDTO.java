package fr.epita.pethome.datamodel.dto;

import lombok.Data;

@Data
public class PetResponseDTO {
    private Integer id;
    private String name;
    private String imageUrl;
    private String petType;
    private Integer ownerId;
    private String ownerName;

    public PetResponseDTO(Integer id, String name, String imageUrl, String petType, Integer ownerId, String ownerName) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.petType = petType;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }
}