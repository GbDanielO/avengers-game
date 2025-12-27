package br.com.avengers.adapters.dto;

import br.com.avengers.domain.model.Personagem;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatalhaDTO {

    private String protocolo;
    private Personagem personagem1;
    private Personagem personagem2;
}
