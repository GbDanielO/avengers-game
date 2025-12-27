package br.com.avengers.domain.model;

import br.com.avengers.domain.enums.MotivoArtefatoOuMagiaNaoUsadaEnum;
import br.com.avengers.domain.enums.TipoUsoEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoBatalha {

    private Integer turno = 0;

    // ===== ATACANTE =====
    private String atacante;
    private boolean atacanteUsouMagia;
    private MotivoArtefatoOuMagiaNaoUsadaEnum motivoAtacanteNaoUsouMagia;
    private TipoUsoEnum atacanteTipoMagia;
    private boolean atacanteUsouArtefato;
    private MotivoArtefatoOuMagiaNaoUsadaEnum motivoAtacanteNaoUsouArtefato;
    private String atacanteNomeArtefato;
    private TipoUsoEnum atacanteTipoUso;
    private Integer ataqueTurno;

    // ===== DEFENSOR =====
    private String defensor;
    private boolean defensorUsouMagia;
    private MotivoArtefatoOuMagiaNaoUsadaEnum motivoDefensorNaoUsouMagia;
    private TipoUsoEnum defensorTipoMagia;
    private boolean defensorUsouArtefato;
    private MotivoArtefatoOuMagiaNaoUsadaEnum motivoDefensorNaoUsouArtefato;
    private String defensorNomeArtefato;
    private TipoUsoEnum defensorTipoUso;
    private Integer defesaTurno;

    private Integer dano;
    private Integer defesaRestante;

}
