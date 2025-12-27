package br.com.avengers.domain.model;

import br.com.avengers.adapters.dto.MagiaDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Magia {

    private Long id;

    private Integer forcaAtaque;

    private Integer forcaDefesa;

    public static Magia getInstanceFrom(MagiaDTO dto) {
        if (dto == null) return null;

        return Magia.builder()
                .id(dto.getId())
                .forcaAtaque(dto.getForcaAtaque())
                .forcaDefesa(dto.getForcaDefesa())
                .build();
    }

}
