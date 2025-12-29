package br.com.avengers.adapters.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "batalha")
public class BatalhaResultadoDTO {

    @Id
    private String id;

    private String dataHoraBatalha;

    @Indexed(unique = true)
    private String protocolo;

    private PersonagemDTO personagem1;

    private PersonagemDTO personagem2;

    private List<TurnoBatalhaDTO> turnos;

    private ResultadoDTO resultado;

}
