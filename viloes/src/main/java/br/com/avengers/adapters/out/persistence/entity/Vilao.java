package br.com.avengers.adapters.out.persistence.entity;

import br.com.avengers.adapters.dto.ViloesDTO;
import br.com.avengers.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "viloes")
public class Vilao {

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

    public static Vilao getInstanceFrom(ViloesDTO dto) {
        if (dto == null) {
            return null;
        }

        return Vilao.builder()
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
        if (!(o instanceof Vilao vilao)) return false;
        return Objects.equals(id, vilao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}