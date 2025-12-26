package br.com.avengers.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JogoResponse {

    private String numeroProtocolo;
    private String mensagem;

}
