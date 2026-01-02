package br.com.avengers.adapters.out.persistence.entity;

import br.com.avengers.adapters.dto.AvengerDTO;
import br.com.avengers.adapters.dto.HabilidadeDTO;
import br.com.avengers.adapters.dto.MagiaDTO;
import br.com.avengers.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avengers")
public class Avenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String apelido;

    @Column(name = "nome_real")
    private String nomeReal;

    private String descricao;

    @Column(length = 2000)
    private String historia;

    private Integer idade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum statusEnum;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "habilidade_id")
    private Habilidade habilidade;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "magia_id")
    private Magia magia;

    public static Avenger getInstanceFrom(AvengerDTO dto) {
        if (dto == null) {
            return null;
        }

        return Avenger.builder()
                .id(dto.getId())
                .apelido(dto.getApelido())
                .nomeReal(dto.getNomeReal())
                .descricao(dto.getDescricao())
                .historia(dto.getHistoria())
                .idade(dto.getIdade())
                .statusEnum(dto.getStatusEnum())
                .habilidade(Habilidade.getInstanceFrom(dto.getHabilidade()))
                .magia(Magia.getInstanceFrom(dto.getMagia()))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Avenger avenger)) return false;
        return Objects.equals(id, avenger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}