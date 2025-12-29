package br.com.avengers.domain.builder;

import br.com.avengers.domain.enums.ArtefatoEnum;
import br.com.avengers.domain.model.*;

public class PersonagemBuilder {

    public static Personagem personagemCompleto(String apelido) {
        return Personagem.builder()
                .apelido(apelido)
                .habilidade(habilidadePadrao())
                .magia(magiaPadrao())
                .artefato(artefatoPadrao())
                .build();
    }

    public static Personagem personagemSemMagia(String apelido) {
        return Personagem.builder()
                .apelido(apelido)
                .habilidade(habilidadePadrao())
                .magia(null)
                .artefato(artefatoPadrao())
                .build();
    }

    public static Personagem personagemSemArtefato(String apelido) {
        return Personagem.builder()
                .apelido(apelido)
                .habilidade(habilidadePadrao())
                .magia(magiaPadrao())
                .artefato(null)
                .build();
    }

    // =========================
    // OBJETOS AUXILIARES
    // =========================

    private static Habilidade habilidadePadrao() {
        return Habilidade.builder()
                .forca(30)
                .armamento(20)
                .inteligencia(50)
                .agilidade(50)
                .velocidade(50)
                .resistencia(20)
                .armadura(30)
                .fatorDeCura(10)
                .magia(30)
                .build();
    }

    private static Magia magiaPadrao() {
        return Magia.builder()
                .forcaAtaque(30)
                .forcaDefesa(20)
                .build();
    }

    private static Artefato artefatoPadrao() {
        return Artefato.builder()
                .artefatoEnum(ArtefatoEnum.CETRO_DE_WATOOMB)
                .forcaAtaque(100)
                .forcaDefesa(90)
                .build();
    }
}

