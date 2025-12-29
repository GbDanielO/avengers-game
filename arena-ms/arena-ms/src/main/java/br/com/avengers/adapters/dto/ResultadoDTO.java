package br.com.avengers.adapters.dto;

import br.com.avengers.domain.model.Resultado;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {

    private String campeao;
    private Integer pontosDefesaRestante;

    public static ResultadoDTO getInstanceFrom(Resultado resultado) {
        if(resultado == null) return null;

        return ResultadoDTO.builder()
                .campeao(resultado.getCampeao())
                .pontosDefesaRestante(resultado.getPontosDefesaRestante())
                .build();

    }
}
