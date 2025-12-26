package br.com.avengers.adapters.out.persistence.entity;

import br.com.avengers.adapters.dto.MagiaDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "magias")
public class Magia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
