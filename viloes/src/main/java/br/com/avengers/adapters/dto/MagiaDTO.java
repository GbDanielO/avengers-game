package br.com.avengers.adapters.dto;

import br.com.avengers.adapters.out.persistence.entity.Magia;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MagiaDTO {

    private Long id;

    @Min(0) @Max(100)
    private Integer forcaAtaque;

    @Min(0) @Max(100)
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
