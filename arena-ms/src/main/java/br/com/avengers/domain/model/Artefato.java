package br.com.avengers.domain.model;

import br.com.avengers.adapters.dto.ArtefatoDTO;
import br.com.avengers.domain.enums.ArtefatoEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artefato {

    private ArtefatoEnum artefatoEnum;
    private Integer forcaAtaque;
    private Integer forcaDefesa;

    public static Artefato getInstanceFrom(ArtefatoDTO artefatoDTO) {
        if(artefatoDTO == null) return null;

        return Artefato.builder()
                .artefatoEnum(artefatoDTO.getArtefatoEnum())
                .forcaAtaque(artefatoDTO.getForcaAtaque())
                .forcaDefesa(artefatoDTO.getForcaDefesa())
                .build();

    }
}
