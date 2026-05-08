// src/main/java/org/example/puntosbonus/dto/AuthToken.java
package org.example.puntosbonus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private String token;
}