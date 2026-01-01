package br.com.avengers.adapters.dto;

import br.com.avengers.domain.model.Personagem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatalhaDTO {

    private String protocolo;
    private Personagem personagem1;
    private Personagem personagem2;

}
