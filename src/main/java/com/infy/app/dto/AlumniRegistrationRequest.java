package com.infy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumniRegistrationRequest {
    private String email;      
    private String password;  
    private String secretKey;  
    private String fullName;
}
