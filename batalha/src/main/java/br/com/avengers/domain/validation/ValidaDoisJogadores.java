package br.com.avengers.domain.validation;

import br.com.avengers.domain.model.DoisJogadores;
import br.com.avengers.domain.model.UmJogador;
import br.com.avengers.shared.NegocioException;

public class ValidaDoisJogadores {

    public static void valida(DoisJogadores doisJogadores) {

        if(doisJogadores.getIdJogo() == null || doisJogadores.getIdJogo().isEmpty())  {

            throw new NegocioException("Deve ser informado um ID para o jogo para poder montar a partida.");
        }

        if(doisJogadores.getApelidoLutador() == null || doisJogadores.getApelidoLutador().isEmpty())  {

            throw new NegocioException("Deve ser informado o nome do personagem para a batalha ser construída.");
        }

        if(doisJogadores.getTipoPersonagem() == null)  {

            throw new NegocioException("Deve ser informado o tipo do personagem (HEROI/VILAO) para a batalha ser construída.");
        }
    }
}
