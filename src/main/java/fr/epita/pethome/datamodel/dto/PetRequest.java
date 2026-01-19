package fr.epita.pethome.datamodel.dto;

import lombok.Data;

@Data
public class PetRequest {
    private String name;
    private String imageUrl;
    private Integer petTypeId;
    private Integer ownerId;
}
