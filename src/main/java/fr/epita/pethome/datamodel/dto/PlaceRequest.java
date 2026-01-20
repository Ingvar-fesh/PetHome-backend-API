package fr.epita.pethome.datamodel.dto;

import lombok.Data;

@Data
public class PlaceRequest {
    private String name;
    private String address;
    private String description;
    private Integer typeId;
    private Double latitude;
    private Double longitude;
}
