package br.com.avengers.adapters.dto;

import br.com.avengers.adapters.out.persistence.entity.Vilao;
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
public class ViloesDTO {

    private Long id;

    @NotBlank(message = "Apelido é obrigatório")
    private String apelido;

    @NotBlank(message = "Nome é obrigatório")
    private String nomeReal;

    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    @NotBlank(message = "História é obrigatório")
    private String historia;

    @NotNull(message = "Idade é obrigatória")
    @Positive(message = "Idade deve ser maior que zero")
    private Integer idade;

    @NotNull(message = "Status é obrigatório")
    private StatusEnum statusEnum = StatusEnum.ATIVO;

    @NotNull(message = "É obrigatório informar as habilidades")
    private HabilidadeDTO habilidade;
    private MagiaDTO magia;

    public static ViloesDTO getInstanceFrom(Vilao entity) {
        if (entity == null) {
            return null;
        }

        return ViloesDTO.builder()
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

