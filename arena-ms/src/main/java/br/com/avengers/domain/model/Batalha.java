package br.com.avengers.domain.model;

import br.com.avengers.adapters.dto.BatalhaDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Batalha {

    private String dataHoraBatalha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            .format(LocalDateTime.now());
    private String protocolo;
    private Personagem personagem1;
    private Personagem personagem2;

    private List<TurnoBatalha> turnos;
    private Resultado resultado;

    public static Batalha getInstanceFrom(BatalhaDTO batalhaDTO){
        if(batalhaDTO == null) return null;

        return Batalha.builder()
                .dataHoraBatalha(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                        .format(LocalDateTime.now()))
                .protocolo(batalhaDTO.getProtocolo())
                .personagem1(batalhaDTO.getPersonagem1())
                .personagem2(batalhaDTO.getPersonagem2())
                .build();

    }

}
