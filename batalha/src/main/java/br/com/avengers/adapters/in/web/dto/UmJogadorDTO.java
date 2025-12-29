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
public class UmJogadorDTO {

    @NotBlank
    private String apelidoLutador1;
    @NotNull
    private TipoPersonagemEnum tipoPersonagem1;
    @NotBlank
    private String apelidoLutador2;
    @NotNull
    private TipoPersonagemEnum tipoPersonagem2;
    private ArtefatoEnum artefatoEnumJogador1;
    private ArtefatoEnum artefatoEnumJogador2;

}
