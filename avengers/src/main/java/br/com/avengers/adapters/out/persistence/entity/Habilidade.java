package br.com.avengers.adapters.out.persistence.entity;

import br.com.avengers.adapters.dto.HabilidadeDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "habilidades")
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
