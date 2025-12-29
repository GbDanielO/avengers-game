package br.com.avengers.adapters.in.web.dto;

import br.com.avengers.domain.enums.ArtefatoEnum;
import br.com.avengers.domain.enums.TipoPersonagemEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String idJogo;
    @NotBlank
    private String apelidoLutador;
    @NotNull
    private TipoPersonagemEnum tipoPersonagem;
    private ArtefatoEnum artefatoEnumJogador;

}
