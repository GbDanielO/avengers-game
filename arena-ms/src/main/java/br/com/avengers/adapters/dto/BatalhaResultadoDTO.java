package br.com.avengers.adapters.dto;

import br.com.avengers.domain.model.Batalha;
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

    public static BatalhaResultadoDTO getInstanceFrom(Batalha batalha) {
        if(batalha == null) return null;

        return BatalhaResultadoDTO.builder()
                .dataHoraBatalha(batalha.getDataHoraBatalha())
                .protocolo(batalha.getProtocolo())
                .personagem1(PersonagemDTO.getInstanceFrom(batalha.getPersonagem1()))
                .personagem2(PersonagemDTO.getInstanceFrom(batalha.getPersonagem2()))
                .turnos(batalha.getTurnos().stream().map(TurnoBatalhaDTO::getInstanceFrom).toList())
                .resultado(ResultadoDTO.getInstanceFrom(batalha.getResultado()))
                .build();
    }
}
