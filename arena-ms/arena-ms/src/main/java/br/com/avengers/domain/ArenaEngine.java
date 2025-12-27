package br.com.avengers.domain;

import br.com.avengers.domain.enums.MotivoArtefatoOuMagiaNaoUsadaEnum;
import br.com.avengers.domain.enums.TipoUsoEnum;
import br.com.avengers.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ArenaEngine {

    private static final Random RANDOM = new Random();

    public Batalha execute(Batalha batalha) {


        Combatente combatente1 = calcularCombatente(batalha.getPersonagem1());
        Combatente combatente2 = calcularCombatente(batalha.getPersonagem2());

        List<TurnoBatalha> turnos = new ArrayList<>();

        Combatente atacante = RANDOM.nextBoolean() ? combatente1 : combatente2;
        Combatente defensor = atacante == combatente1 ? combatente2 : combatente1;

        int numeroTurno = 1;

        // turnos da batalha
        while (defensor.getDefesaAtual() > 0) {

            Integer ataqueTurno = atacante.getAtaqueBase();
            Integer defesaTurno = defensor.getDefesaAtual();

            // ======================
            // ATACANTE - MAGIA
            // ======================
            boolean aUsouMagia = false;
            TipoUsoEnum aTipoMagia = null;
            MotivoArtefatoOuMagiaNaoUsadaEnum aMotivoMagia = null;

            if (atacante.getPersonagem().getMagia() != null
                    && atacante.getPersonagem().getHabilidade().getMagia() > 0
                    && RANDOM.nextBoolean()) {

                aUsouMagia = true;
                aTipoMagia = RANDOM.nextBoolean() ? TipoUsoEnum.ATAQUE : TipoUsoEnum.DEFESA;

                if (aTipoMagia == TipoUsoEnum.ATAQUE) {
                    ataqueTurno += atacante.getPersonagem().getHabilidade().getMagia()
                            * atacante.getPersonagem().getMagia().getForcaAtaque();
                }
                // DEFESA do atacante aqui não ajuda em nada → proposital
            } else if (atacante.getPersonagem().getMagia() != null) {
                aMotivoMagia = motivoRandomico();
            }

            // ======================
            // DEFENSOR - MAGIA
            // ======================
            boolean dUsouMagia = false;
            TipoUsoEnum dTipoMagia = null;
            MotivoArtefatoOuMagiaNaoUsadaEnum dMotivoMagia = null;

            if (defensor.getPersonagem().getMagia() != null
                    && defensor.getPersonagem().getHabilidade().getMagia() > 0
                    && RANDOM.nextBoolean()) {

                dUsouMagia = true;
                dTipoMagia = RANDOM.nextBoolean() ? TipoUsoEnum.ATAQUE : TipoUsoEnum.DEFESA;

                if (dTipoMagia == TipoUsoEnum.DEFESA) {
                    defesaTurno += defensor.getPersonagem().getHabilidade().getMagia()
                            * defensor.getPersonagem().getMagia().getForcaDefesa();
                }
                // ATAQUE do defensor aqui não ajuda → proposital
            } else if (defensor.getPersonagem().getMagia() != null) {
                dMotivoMagia = motivoRandomico();
            }

            // ======================
            // ATACANTE - ARTEFATO
            // ======================
            boolean aUsouArtefato = false;
            TipoUsoEnum aTipoUsoArtefato = null;
            MotivoArtefatoOuMagiaNaoUsadaEnum aMotivoArtefato = null;

            Artefato artefatoAtacante = atacante.getPersonagem().getArtefato();

            if (artefatoAtacante != null && RANDOM.nextBoolean()) {
                aUsouArtefato = true;
                aTipoUsoArtefato = RANDOM.nextBoolean() ? TipoUsoEnum.ATAQUE : TipoUsoEnum.DEFESA;

                if (aTipoUsoArtefato == TipoUsoEnum.ATAQUE) {
                    ataqueTurno += artefatoAtacante.getForcaAtaque();
                }
            } else if (artefatoAtacante != null) {
                aMotivoArtefato = motivoRandomico();
            }

            // ======================
            // DEFENSOR - ARTEFATO
            // ======================
            boolean dUsouArtefato = false;
            TipoUsoEnum dTipoUsoArtefato = null;
            MotivoArtefatoOuMagiaNaoUsadaEnum dMotivoArtefato = null;

            Artefato artefatoDefensor = defensor.getPersonagem().getArtefato();

            if (artefatoDefensor != null && RANDOM.nextBoolean()) {
                dUsouArtefato = true;
                dTipoUsoArtefato = RANDOM.nextBoolean() ? TipoUsoEnum.ATAQUE : TipoUsoEnum.DEFESA;

                if (dTipoUsoArtefato == TipoUsoEnum.DEFESA) {
                    defesaTurno += artefatoDefensor.getForcaDefesa();
                }
            } else if (artefatoDefensor != null) {
                dMotivoArtefato = motivoRandomico();
            }

            // ======================
            // CÁLCULO FINAL
            // ======================
            int dano = Math.max(0, ataqueTurno - defesaTurno);
            defensor.setDefesaAtual(defensor.getDefesaAtual() - dano);

            // ======================
            // REGISTRO DO TURNO
            // ======================
            TurnoBatalha turno = new TurnoBatalha(
                    numeroTurno++,

                    atacante.getPersonagem().getApelido(),
                    aUsouMagia,
                    aMotivoMagia,
                    aTipoMagia,
                    aUsouArtefato,
                    aMotivoArtefato,
                    artefatoAtacante != null ? artefatoAtacante.getArtefatoEnum().name() : null,
                    aTipoUsoArtefato,
                    ataqueTurno,

                    defensor.getPersonagem().getApelido(),
                    dUsouMagia,
                    dMotivoMagia,
                    dTipoMagia,
                    dUsouArtefato,
                    dMotivoArtefato,
                    artefatoDefensor != null ? artefatoDefensor.getArtefatoEnum().name() : null,
                    dTipoUsoArtefato,
                    defesaTurno,

                    dano,
                    Math.max(defensor.getDefesaAtual(), 0)
            );

            turnos.add(turno);

            if (defensor.getDefesaAtual() <= 0) break;

            Combatente temp = atacante;
            atacante = defensor;
            defensor = temp;
        }

        batalha.setTurnos(turnos);
        batalha.setResultado(new Resultado(
                atacante.getPersonagem().getApelido(),
                Math.max(atacante.getDefesaAtual(), 0)
        ));

        return batalha;
    }

    // =========================
    // MÉTODOS PRIVADOS
    // =========================

    private Combatente calcularCombatente(Personagem personagem) {
        return new Combatente(
                personagem,
                calcularAtaqueBase(personagem),
                calcularDefesaBase(personagem)
        );
    }

    private int calcularAtaqueBase(Personagem p) {
        Habilidade h = p.getHabilidade();
        return h.getForca()
                + h.getArmamento()
                + h.getInteligencia()
                + h.getAgilidade()
                + h.getVelocidade();
    }

    private int calcularDefesaBase(Personagem p) {
        Habilidade h = p.getHabilidade();
        return h.getResistencia()
                + h.getArmadura()
                + h.getFatorDeCura()
                + h.getInteligencia()
                + h.getAgilidade()
                + h.getVelocidade();
    }

    private MotivoArtefatoOuMagiaNaoUsadaEnum motivoRandomico() {
        MotivoArtefatoOuMagiaNaoUsadaEnum[] valores =
                MotivoArtefatoOuMagiaNaoUsadaEnum.values();
        return valores[RANDOM.nextInt(valores.length)];
    }

}
