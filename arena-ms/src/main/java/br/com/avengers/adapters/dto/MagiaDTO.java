package br.com.avengers.adapters.dto;

import br.com.avengers.domain.model.Magia;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MagiaDTO {

    private Long id;

    private Integer forcaAtaque;

    private Integer forcaDefesa;

    public static MagiaDTO getInstanceFrom(Magia entity) {
        if (entity == null) return null;

        return MagiaDTO.builder()
                .id(entity.getId())
                .forcaAtaque(entity.getForcaAtaque())
                .forcaDefesa(entity.getForcaDefesa())
                .build();
    }

}
