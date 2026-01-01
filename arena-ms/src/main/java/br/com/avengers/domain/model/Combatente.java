package br.com.avengers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Combatente {

    private Personagem personagem;

    private int ataqueBase;

    @Setter
    private int defesaAtual;

}
