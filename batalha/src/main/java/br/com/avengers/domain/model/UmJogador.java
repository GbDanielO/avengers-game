package br.com.avengers.domain.model;

import br.com.avengers.adapters.in.web.dto.UmJogadorDTO;
import br.com.avengers.domain.enums.ArtefatoEnum;
import br.com.avengers.domain.enums.TipoPersonagemEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Um jogador manda os 2 personagens de uma vez
 * Mas 2 Jogadores cada um manda o seu
 */
public class UmJogador {

    private String apelidoLutador1;
    private TipoPersonagemEnum tipoPersonagem1;
    private String apelidoLutador2;
    private TipoPersonagemEnum tipoPersonagem2;
    private ArtefatoEnum artefatoEnumJogador1;
    private ArtefatoEnum artefatoEnumJogador2;

    public static UmJogador getInstanceFrom(UmJogadorDTO umJogadorDTO) {
        if(umJogadorDTO == null){
            return null;
        }
        return UmJogador.builder()
                .apelidoLutador1(umJogadorDTO.getApelidoLutador1())
                .tipoPersonagem1(umJogadorDTO.getTipoPersonagem1())
                .apelidoLutador2(umJogadorDTO.getApelidoLutador2())
                .tipoPersonagem2(umJogadorDTO.getTipoPersonagem2())
                .artefatoEnumJogador1(umJogadorDTO.getArtefatoEnumJogador1())
                .artefatoEnumJogador2(umJogadorDTO.getArtefatoEnumJogador2())
                .build();
    }

}
