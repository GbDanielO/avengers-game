package br.com.avengers.domain.model;

import br.com.avengers.adapters.dto.HabilidadeDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habilidade {

    private Long id;

    private Integer resistencia;
    private Integer forca;
    private Integer inteligencia;
    private Integer armamento;
    private Integer armadura;
    private Integer fatorDeCura;
    private Integer agilidade;
    private Integer velocidade;
    private Integer magia;

    public static Habilidade getInstanceFrom(HabilidadeDTO dto) {
        if (dto == null) return null;

        return Habilidade.builder()
                .resistencia(dto.getResistencia())
                .forca(dto.getForca())
                .inteligencia(dto.getInteligencia())
                .armamento(dto.getArmamento())
                .armadura(dto.getArmadura())
                .fatorDeCura(dto.getFatorDeCura())
                .agilidade(dto.getAgilidade())
                .velocidade(dto.getVelocidade())
                .magia(dto.getMagia())
                .build();
    }

}
