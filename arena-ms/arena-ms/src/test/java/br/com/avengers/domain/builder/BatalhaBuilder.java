package br.com.avengers.domain.builder;

import br.com.avengers.domain.model.Batalha;

public class BatalhaBuilder {

    public static Batalha batalhaSimples() {
        return Batalha.builder()
                .protocolo("PROTO-TESTE-001")
                .personagem1(PersonagemBuilder.personagemCompleto("Thor"))
                .personagem2(PersonagemBuilder.personagemCompleto("Thanos"))
                .build();
    }

    public static Batalha batalhaSemMagia() {
        return Batalha.builder()
                .protocolo("PROTO-TESTE-SEM-MAGIA")
                .personagem1(PersonagemBuilder.personagemSemMagia("Hulk"))
                .personagem2(PersonagemBuilder.personagemSemMagia("Ultron"))
                .build();
    }

    public static Batalha batalhaSemArtefato() {
        return Batalha.builder()
                .protocolo("PROTO-TESTE-SEM-ARTEFATO")
                .personagem1(PersonagemBuilder.personagemSemArtefato("Capitão"))
                .personagem2(PersonagemBuilder.personagemSemArtefato("Loki"))
                .build();
    }
}
