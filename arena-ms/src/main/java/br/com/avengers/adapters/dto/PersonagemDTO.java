package br.com.avengers.adapters.dto;

import br.com.avengers.domain.enums.StatusEnum;
import br.com.avengers.domain.model.Personagem;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonagemDTO {

    private Long id;

    private String apelido;

    private String nomeReal;

    private String descricao;

    private String historia;

    private Integer idade;

    private StatusEnum statusEnum = StatusEnum.ATIVO;

    private HabilidadeDTO habilidade;
    private MagiaDTO magia;
    private ArtefatoDTO artefato;

    public static PersonagemDTO getInstanceFrom(Personagem personagem) {
        if (personagem == null) {
            return null;
        }

        return PersonagemDTO.builder()
                .id(personagem.getId())
                .apelido(personagem.getApelido())
                .nomeReal(personagem.getNomeReal())
                .descricao(personagem.getDescricao())
                .historia(personagem.getHistoria())
                .idade(personagem.getIdade())
                .statusEnum(personagem.getStatusEnum())
                .habilidade(HabilidadeDTO.getInstanceFrom(personagem.getHabilidade()))
                .magia(MagiaDTO.getInstanceFrom(personagem.getMagia()))
                .artefato(ArtefatoDTO.getInstanceFrom(personagem.getArtefato()))
                .build();
    }

}

