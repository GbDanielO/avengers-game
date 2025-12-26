package br.com.avengers.adapters.in.web.dto;

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
public class UmJogadorDTO {

    private String apelidoLutador1;
    private TipoPersonagemEnum tipoPersonagem1;
    private String apelidoLutador2;
    private TipoPersonagemEnum tipoPersonagem2;
    private ArtefatoEnum artefatoEnumJogador1;
    private ArtefatoEnum artefatoEnumJogador2;

}
