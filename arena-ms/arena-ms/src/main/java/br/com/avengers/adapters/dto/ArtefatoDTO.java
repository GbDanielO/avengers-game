package br.com.avengers.adapters.dto;

import br.com.avengers.domain.enums.ArtefatoEnum;
import br.com.avengers.domain.model.Artefato;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtefatoDTO {

    private ArtefatoEnum artefatoEnum;
    private Integer forcaAtaque;
    private Integer forcaDefesa;

    public static ArtefatoDTO getInstanceFrom(Artefato artefato) {
        if(artefato == null) return null;

        return ArtefatoDTO.builder()
                .artefatoEnum(artefato.getArtefatoEnum())
                .forcaAtaque(artefato.getForcaAtaque())
                .forcaDefesa(artefato.getForcaDefesa())
                .build();

    }
}
