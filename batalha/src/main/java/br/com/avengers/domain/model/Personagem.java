package br.com.avengers.domain.model;

import br.com.avengers.adapters.dto.PersonagemDTO;
import br.com.avengers.domain.enums.StatusEnum;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Personagem implements Serializable {

    private Long id;

    private String apelido;

    private String nomeReal;

    private String descricao;

    private String historia;

    private Integer idade;

    private StatusEnum statusEnum;

    private Habilidade habilidade;

    private Magia magia;

    private Artefato artefato;

    public static Personagem getInstanceFrom(PersonagemDTO dto) {
        if (dto == null) {
            return null;
        }

        return Personagem.builder()
                .id(dto.getId())
                .apelido(dto.getApelido())
                .nomeReal(dto.getNomeReal())
                .descricao(dto.getDescricao())
                .historia(dto.getHistoria())
                .idade(dto.getIdade())
                .statusEnum(dto.getStatusEnum())
                .habilidade(Habilidade.getInstanceFrom(dto.getHabilidade()))
                .magia(Magia.getInstanceFrom(dto.getMagia()))
                .artefato(Artefato.getInstanceFrom(dto.getArtefato()))
                .build();
    }

}