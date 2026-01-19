package br.com.avengers.domain.validation;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.model.Habilidade;
import br.com.avengers.domain.model.Personagem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidaHabilidade extends AbstractValidador{
    @Override
    public void valida(Batalha batalha, List<String> erros) {
        if(batalha.getPersonagem1().getHabilidade() == null || batalha.getPersonagem2().getHabilidade() == null){
            erros.add("É necessário informar as habilidades dos personagens para a batalha!");
            chamaProximo(batalha, erros);
        }

        Personagem personagem = batalha.getPersonagem1();
        validaHabilidade(erros, personagem);
        personagem = batalha.getPersonagem2();
        validaHabilidade(erros, personagem);

        chamaProximo(batalha, erros);
    }

    private void validaHabilidade(List<String> erros, Personagem personagem) {
        Habilidade h = personagem.getHabilidade();
        if(h.getForca() == null || h.getResistencia() == null || h.getAgilidade() == null){
            erros.add("Os atributos físicos (Força, Resistência e Agilidade) são obrigatórios!");
        }
        if(h.getInteligencia() == null || h.getVelocidade() == null){
            erros.add("Os atributos mentais e de movimento são obrigatórios!");
        }
        if(h.getArmamento() == null || h.getArmadura() == null){
            erros.add("Os atributos de equipamento (Arma/Armadura) devem ser informados!");
        }
    }
}
