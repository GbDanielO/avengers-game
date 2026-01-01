package br.com.avengers.adapters.dto;

import br.com.avengers.domain.model.Habilidade;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabilidadeDTO {

    private Integer resistencia;

    private Integer forca;

    private Integer inteligencia;

    private Integer armamento;

    private Integer armadura;

    private Integer fatorDeCura;

    private Integer agilidade;

    private Integer velocidade;

    // Valor base de afinidade mágica do vingador
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
