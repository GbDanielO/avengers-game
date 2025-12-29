package br.com.avengers.adapters.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {

    private String campeao;
    private Integer pontosDefesaRestante;

}
