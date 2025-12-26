package br.com.avengers.adapters.dto;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.adapters.out.persistence.entity.Habilidade;
import br.com.avengers.adapters.out.persistence.entity.Magia;
import br.com.avengers.domain.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvengerDTO {

    private Long id;

    @NotBlank(message = "Apelido é obrigatório")
    private String apelido;

    @NotBlank(message = "Nome é obrigatório")
    private String nomeReal;

    private String descricao;

    private String historia;

    @NotNull(message = "Idade é obrigatória")
    @Positive(message = "Idade deve ser maior que zero")
    private Integer idade;

    private StatusEnum statusEnum = StatusEnum.ATIVO;

    private HabilidadeDTO habilidade;
    private MagiaDTO magia;

    public static AvengerDTO getInstanceFrom(Avenger entity) {
        if (entity == null) {
            return null;
        }

        return AvengerDTO.builder()
                .id(entity.getId())
                .apelido(entity.getApelido())
                .nomeReal(entity.getNomeReal())
                .descricao(entity.getDescricao())
                .historia(entity.getHistoria())
                .idade(entity.getIdade())
                .statusEnum(entity.getStatusEnum())
                .habilidade(HabilidadeDTO.getInstanceFrom(entity.getHabilidade()))
                .magia(MagiaDTO.getInstanceFrom(entity.getMagia()))
                .build();
    }

}

