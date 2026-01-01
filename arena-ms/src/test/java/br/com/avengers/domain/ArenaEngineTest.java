package br.com.avengers.domain;


import br.com.avengers.domain.builder.BatalhaBuilder;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.model.Resultado;
import br.com.avengers.domain.model.TurnoBatalha;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArenaEngineTest {
    @Test
    void deveExecutarBatalhaEGerarResultado() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSimples();

        Batalha resultado = engine.execute(batalha);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getResultado()).isNotNull();
        Assertions.assertThat(resultado.getResultado().getCampeao()).isNotBlank();
    }

    @Test
    void deveGerarAoMenosUmTurno() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSimples();

        Batalha resultado = engine.execute(batalha);

        Assertions.assertThat(resultado.getTurnos())
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void deveEncerrarBatalhaQuandoDefesaDoDerrotadoForZero() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSimples();

        Batalha resultado = engine.execute(batalha);

        TurnoBatalha turnoBatalha = resultado.getTurnos().get(resultado.getTurnos().size() - 1);

        Assertions.assertThat(turnoBatalha.getDefesaRestante()).isEqualTo(0);
    }

    @Test
    void nenhumTurnoDeveTerDanoNegativo() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSimples();

        Batalha resultado = engine.execute(batalha);

        resultado.getTurnos().forEach(turno ->
                Assertions.assertThat(turno.getDano()).isGreaterThanOrEqualTo(0)
        );
    }

    @Test
    void defesaFinalDoTurnoNaoPodeSerNegativa() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSimples();

        Batalha resultado = engine.execute(batalha);

        resultado.getTurnos().forEach(turno ->
                Assertions.assertThat(turno.getDefesaRestante()).isGreaterThanOrEqualTo(0)
        );
    }

    @Test
    void personagemSemMagiaNaoPodeUsarMagia() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSemMagia();

        Batalha resultado = engine.execute(batalha);

        resultado.getTurnos().forEach(turno -> {
            Assertions.assertThat(turno.isAtacanteUsouMagia()).isFalse();
            Assertions.assertThat(turno.isDefensorUsouMagia()).isFalse();
        });
    }

    @Test
    void personagemSemArtefatoNaoPodeUsarArtefato() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSemArtefato();

        Batalha resultado = engine.execute(batalha);

        resultado.getTurnos().forEach(turno -> {
            Assertions.assertThat(turno.isAtacanteUsouArtefato()).isFalse();
            Assertions.assertThat(turno.isDefensorUsouArtefato()).isFalse();
        });
    }

    @Test
    void vencedorDeveSerUmDosPersonagens() {
        ArenaEngine engine = new ArenaEngine();

        Batalha batalha = BatalhaBuilder.batalhaSimples();

        Batalha resultado = engine.execute(batalha);

        String vencedor = resultado.getResultado().getCampeao();

        Assertions.assertThat(vencedor).isIn(
                batalha.getPersonagem1().getApelido(),
                batalha.getPersonagem2().getApelido()
        );
    }
}
