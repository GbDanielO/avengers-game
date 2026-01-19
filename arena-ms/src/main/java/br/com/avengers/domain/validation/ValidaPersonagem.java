package br.com.avengers.domain.validation;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.model.Personagem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidaPersonagem extends AbstractValidador{

    @Override
    public void valida(Batalha batalha, List<String> erros) {

        if(batalha.getPersonagem1() == null || batalha.getPersonagem2() == null){
            erros.add("É necessário informar os personagens para a batalha!");
            return;
        }
        Personagem personagem = batalha.getPersonagem1();
        validaPersonagem(erros, personagem);
        personagem = batalha.getPersonagem2();
        validaPersonagem(erros, personagem);

        chamaProximo(batalha, erros);
    }

    private static void validaPersonagem(List<String> erros, Personagem personagem) {
        // Validação de Identificação e Descrição
        if(personagem.getApelido() == null || personagem.getApelido().isEmpty()){
            erros.add("O apelido do personagem é obrigatório!");
        }

        if(personagem.getNomeReal() == null || personagem.getNomeReal().isEmpty()){
            erros.add("O nome real do personagem é obrigatório!");
        }

        if(personagem.getIdade() == null || personagem.getIdade() <= 0){
            erros.add("É necessário informar uma idade válida para o personagem!");
        }

        // Validação de Status e História
        if(personagem.getStatusEnum() == null){
            erros.add("O status do personagem (ex: ATIVO) deve ser informado!");
        }

        if(personagem.getDescricao() == null || personagem.getDescricao().isEmpty()){
            erros.add("A descrição do personagem não pode estar vazia!");
        }

        if(personagem.getHistoria() == null || personagem.getHistoria().isEmpty()){
            erros.add("A história do personagem deve ser preenchida!");
        }
    }
}
