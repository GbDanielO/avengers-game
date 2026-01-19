package br.com.avengers.domain.validation;

import br.com.avengers.domain.model.UmJogador;
import br.com.avengers.shared.NegocioException;

public class ValidaUmJogador {

    public static void valida(UmJogador umJogador) {
        if(umJogador.getApelidoLutador1() == null || umJogador.getApelidoLutador1().isEmpty()
          || umJogador.getApelidoLutador2() == null || umJogador.getApelidoLutador2().isEmpty())  {

            throw new NegocioException("Deve ser informado o nome dos personagens para a batalha ser construída.");
        }

        if(umJogador.getTipoPersonagem1() == null || umJogador.getTipoPersonagem2() == null)  {

            throw new NegocioException("Deve ser informado o tipo dos personagens (HEROI/VILAO) para a batalha ser construída.");
        }
    }
}
