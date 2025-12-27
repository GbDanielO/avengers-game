package br.com.avengers.adapters.dto;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.model.Personagem;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatalhaDTO {

    private String protocolo;
    private Personagem personagem1;
    private Personagem personagem2;

    public static BatalhaDTO getInstanceFrom(Batalha batalha){
        if(batalha == null) return  null;

        return BatalhaDTO.builder()
                .protocolo(batalha.getProtocolo())
                .personagem1(batalha.getPersonagem1())
                .personagem2(batalha.getPersonagem2())
                .build();
    }
}
