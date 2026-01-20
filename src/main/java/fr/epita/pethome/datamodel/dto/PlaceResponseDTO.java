package fr.epita.pethome.datamodel.dto;

import fr.epita.pethome.datamodel.PlaceType;
import lombok.Data;

@Data
public class PlaceResponseDTO {
    private Integer id;
    private String name;
    private String address;
    private String typeName;
    private Double latitude;
    private Double longitude;

    public PlaceResponseDTO(Integer id,
                            String name,
                            String address,
                            String typeName,
                            Double latitude,
                            Double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.typeName = typeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
