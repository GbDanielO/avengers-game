package br.com.avengers.domain.enums;

import lombok.Getter;

@Getter
public enum ArtefatoEnum {

    MANOPLA_DO_INFINITO( 1000, 1000,"Artefato supremo capaz de manipular todos os aspectos da realidade.", "Criada pelos anões de Nidavellir, a Manopla do Infinito foi forjada para canalizar o poder combinado das seis Joias do Infinito. Quando completa, concede poder absoluto sobre o universo."),
    JOIA_DA_ALMA(650,650,"Concede domínio sobre a vida, a morte e as almas.", "A mais misteriosa das joias, exige um sacrifício para ser obtida e permite manipular essências vitais."),
    JOIA_DA_REALIDADE (800, 400, "Permite alterar as leis da física e da realidade.", "Conhecida como Éter, pode transformar matéria, energia e até conceitos fundamentais da existência."),
    JOIA_DO_ESPACO (600, 600, "Permite manipular o espaço e se teletransportar instantaneamente.", "Conhecida como Tesseract, esta joia concede domínio sobre distância, posição e movimento no universo."),
    JOIA_DA_MENTE (700, 600, "Amplifica inteligência, consciência e controle mental.", "Capaz de controlar mentes e expandir capacidades intelectuais, foi usada para criar o Visão."),
    JOIA_DO_TEMPO (300, 900, "Permite manipular o fluxo do tempo.", "Guardada em Kamar-Taj, a Joia do Tempo concede controle sobre passado, presente e futuros possíveis."),
    JOIA_DO_PODER (900, 200, "Fonte de energia bruta e destruição massiva.", "A Joia do Poder concede força física incomensurável e capacidade de destruir planetas inteiros."),
    MIJONIR (850, 500, "Martelo encantado que canaliza o poder do trovão.", "Forjado em Nidavellir e encantado por Odin, apenas os dignos podem empunhar Mjolnir e acessar seu poder."),
    CETRO_DE_WATOOMB (750, 400, "Artefato místico capaz de canalizar energias dimensionais.", "O Cetro de Watoomb permite ao portador acessar forças arcanas devastadoras de outras dimensões."),
    DARKHOLD (700, 300, "Livro proibido de magia negra e conhecimento corruptor.", "O Darkhold contém feitiços ancestrais capazes de dobrar a realidade ao custo da sanidade do usuário."),
    LIVRO_DOS_VISHANTI (400, 800, "Fonte de magia pura e defensiva.", "Antítese do Darkhold, o Livro dos Vishanti reúne feitiços de proteção, cura e equilíbrio místico.");

    private final Integer forcaAtaque;
    private final Integer forcaDefesa;
    private final String descricao;
    private final String historia;

    private ArtefatoEnum(Integer forcaAtaque, Integer forcaDefesa, String descricao, String historia) {
        this.forcaAtaque = forcaAtaque;
        this.forcaDefesa = forcaDefesa;
        this.descricao = descricao;
        this.historia = historia;
    }

}
