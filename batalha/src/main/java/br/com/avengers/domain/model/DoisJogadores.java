package br.com.avengers.domain.model;

import br.com.avengers.adapters.in.web.dto.DoisJogadoresDTO;
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
public class DoisJogadores {

    private String idJogo;
    private String protocoloId;
    private String apelidoLutador;
    private TipoPersonagemEnum tipoPersonagem;
    private ArtefatoEnum artefatoEnumJogador;

    public static DoisJogadores getInstanceFrom(DoisJogadoresDTO doisJogadoresDTO){
        if(doisJogadoresDTO == null) return null;

        return DoisJogadores.builder()
                    .idJogo(doisJogadoresDTO.getIdJogo())
                    .apelidoLutador(doisJogadoresDTO.getApelidoLutador())
                    .tipoPersonagem(doisJogadoresDTO.getTipoPersonagem())
                    .artefatoEnumJogador(doisJogadoresDTO.getArtefatoEnumJogador())
                    .build();
    }

}
