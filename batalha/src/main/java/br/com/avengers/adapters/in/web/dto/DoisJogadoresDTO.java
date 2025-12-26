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
public class DoisJogadoresDTO {

    private String idJogo;
    private String apelidoLutador;
    private TipoPersonagemEnum tipoPersonagem;
    private ArtefatoEnum artefatoEnumJogador;

}
