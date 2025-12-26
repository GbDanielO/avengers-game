package br.com.avengers.adapters.dto;

import br.com.avengers.adapters.out.persistence.entity.Habilidade;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HabilidadeDTO {

    @Min(0) @Max(100)
    private Integer resistencia;

    @Min(0) @Max(100)
    private Integer forca;

    @Min(0) @Max(100)
    private Integer inteligencia;

    @Min(0) @Max(100)
    private Integer armamento;

    @Min(0) @Max(100)
    private Integer armadura;

    @Min(0) @Max(100)
    private Integer fatorDeCura;

    @Min(0) @Max(100)
    private Integer agilidade;

    @Min(0) @Max(100)
    private Integer velocidade;

    // Valor base de afinidade mágica do vingador
    @Min(0) @Max(100)
    private Integer magia;

    public static HabilidadeDTO getInstanceFrom(Habilidade entity) {
        if (entity == null) return null;

        return HabilidadeDTO.builder()
                .resistencia(entity.getResistencia())
                .forca(entity.getForca())
                .inteligencia(entity.getInteligencia())
                .armamento(entity.getArmamento())
                .armadura(entity.getArmadura())
                .fatorDeCura(entity.getFatorDeCura())
                .agilidade(entity.getAgilidade())
                .velocidade(entity.getVelocidade())
                .magia(entity.getMagia())
                .build();
    }
}
