package fr.epita.pethome.datamodel.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
