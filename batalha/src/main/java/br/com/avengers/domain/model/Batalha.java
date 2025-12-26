package br.com.avengers.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Batalha {

    private String protocolo;
    private Personagem personagem1;
    private Personagem personagem2;
}
