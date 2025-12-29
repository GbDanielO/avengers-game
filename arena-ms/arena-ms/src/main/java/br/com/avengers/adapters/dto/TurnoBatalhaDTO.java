package br.com.avengers.adapters.dto;

import br.com.avengers.domain.enums.MotivoArtefatoOuMagiaNaoUsadaEnum;
import br.com.avengers.domain.enums.TipoUsoEnum;
import br.com.avengers.domain.model.TurnoBatalha;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnoBatalhaDTO {

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

    public static TurnoBatalhaDTO getInstanceFrom(TurnoBatalha turnoBatalha) {
        if (turnoBatalha == null) {
            return null;
        }

        TurnoBatalhaDTO dto = new TurnoBatalhaDTO();

        dto.setTurno(turnoBatalha.getTurno());

        // Mapeamento Atacante
        dto.setAtacante(turnoBatalha.getAtacante());
        dto.setAtacanteUsouMagia(turnoBatalha.isAtacanteUsouMagia());
        dto.setMotivoAtacanteNaoUsouMagia(turnoBatalha.getMotivoAtacanteNaoUsouMagia());
        dto.setAtacanteTipoMagia(turnoBatalha.getAtacanteTipoMagia());
        dto.setAtacanteUsouArtefato(turnoBatalha.isAtacanteUsouArtefato());
        dto.setMotivoAtacanteNaoUsouArtefato(turnoBatalha.getMotivoAtacanteNaoUsouArtefato());
        dto.setAtacanteNomeArtefato(turnoBatalha.getAtacanteNomeArtefato());
        dto.setAtacanteTipoUso(turnoBatalha.getAtacanteTipoUso());
        dto.setAtaqueTurno(turnoBatalha.getAtaqueTurno());

        // Mapeamento Defensor
        dto.setDefensor(turnoBatalha.getDefensor());
        dto.setDefensorUsouMagia(turnoBatalha.isDefensorUsouMagia());
        dto.setMotivoDefensorNaoUsouMagia(turnoBatalha.getMotivoDefensorNaoUsouMagia());
        dto.setDefensorTipoMagia(turnoBatalha.getDefensorTipoMagia());
        dto.setDefensorUsouArtefato(turnoBatalha.isDefensorUsouArtefato());
        dto.setMotivoDefensorNaoUsouArtefato(turnoBatalha.getMotivoDefensorNaoUsouArtefato());
        dto.setDefensorNomeArtefato(turnoBatalha.getDefensorNomeArtefato());
        dto.setDefensorTipoUso(turnoBatalha.getDefensorTipoUso());
        dto.setDefesaTurno(turnoBatalha.getDefesaTurno());

        // Resultados
        dto.setDano(turnoBatalha.getDano());
        dto.setDefesaRestante(turnoBatalha.getDefesaRestante());

        return dto;
    }

}
