package br.com.avengers.domain.validation;

import br.com.avengers.adapters.out.persistence.entity.Habilidade;
import br.com.avengers.adapters.out.persistence.entity.Magia;
import br.com.avengers.adapters.out.persistence.entity.Vilao;
import br.com.avengers.domain.ViloesService;
import br.com.avengers.domain.enums.StatusEnum;
import br.com.avengers.ports.out.ViloesRepositoryPort;
import br.com.avengers.shared.ValidacaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ValidacaoTest {

    private ValidacaoChain validacaoChain;

    private Vilao vilao;

    @Mock
    private ViloesRepositoryPort viloesRepositoryPort;

    @BeforeEach
    public void preparaTestes(){

        List<Validador> list = new ArrayList<>(List.of(new ValidaApelido(), new ValidaNome(), new ValidaDescricao(), new ValidaHistoria()));

        validacaoChain = new ValidacaoChain(list);

        vilao = Vilao.builder()
                .apelido("Thanos")
                .nomeReal("Thanos")
                .descricao(
                        "Titã louco, obcecado por equilíbrio universal"
                )
                .historia(
                        "Titã super poderoso que quer destruir metade de todos os seres vivos do Universo " +
                                "na tentativa de salvar a outra metade de um possível colápso de recursos."
                )
                .idade(1000)
                .statusEnum(StatusEnum.ATIVO)
                .habilidade(
                        Habilidade.builder()
                                .resistencia(10)
                                .forca(10)
                                .inteligencia(99)
                                .armamento(80)
                                .armadura(70)
                                .fatorDeCura(0)
                                .agilidade(65)
                                .velocidade(75)
                                .magia(60)
                                .build()
                )
                .magia(Magia.builder()
                        .forcaAtaque(40)
                        .forcaDefesa(30)
                        .build())
                .build();
    }

    @Test
    public void testeCaminhoFeliz(){

        ViloesService viloesService = new ViloesService(viloesRepositoryPort, validacaoChain);

        Mockito.when(viloesRepositoryPort.create(vilao)).thenReturn(vilao);

        Vilao resultado = viloesService.create(vilao);

        Assertions.assertEquals(resultado, vilao);

    }

    @Test
    public void testeErrosValidacao(){

        vilao.setApelido("Th@nos");
        vilao.setNomeReal("Thano$");

        ViloesService viloesService = new ViloesService(viloesRepositoryPort, validacaoChain);

        ValidacaoException ex = Assertions.assertThrows(
                ValidacaoException.class,
                () -> viloesService.create(vilao)
        );

        Assertions.assertFalse(ex.getErros().isEmpty());
        Mockito.verify(viloesRepositoryPort, Mockito.never())
                .create(Mockito.any());

    }
}
