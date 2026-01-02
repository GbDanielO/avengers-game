package br.com.avengers.domain.validations;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.adapters.out.persistence.entity.Habilidade;
import br.com.avengers.domain.AvengerService;
import br.com.avengers.domain.enums.StatusEnum;
import br.com.avengers.ports.out.AvengerRepositoryPort;
import br.com.avengers.shared.ValidacaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidacaoTeste {

    private ValidacaoChain validacaoChain;

    private Avenger avenger;

    @Mock
    private AvengerRepositoryPort avengerRepositoryPort;

    @BeforeEach
    public void montaObjetos(){

        validacaoChain = new ValidacaoChain(new ValidaNome(), new ValidaApelido(), new ValidaDescricao(), new ValidaHistoria());

        avenger = Avenger.builder()
                .apelido("Homem de Ferro")
                .nomeReal("Tony Stark")
                .descricao(
                        "Gênio, bilionário, playboy e filantropo com uma armadura tecnológica de ponta."
                )
                .historia(
                        "Após ser sequestrado, Tony Stark constrói uma armadura para escapar, " +
                                "decidindo usar sua tecnologia para proteger o mundo como um Vingador."
                )
                .idade(48)
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
                                .magia(0)
                                .build()
                )
                .magia(null)
                .build();

    }

    @Test
    public void caminhoFeliz(){
        AvengerService avengerService = new AvengerService(avengerRepositoryPort, validacaoChain);

        Mockito.when(avengerRepositoryPort.create(avenger)).thenReturn(avenger);

        Avenger resultado = avengerService.create(avenger);

        Assertions.assertEquals(avenger, resultado);
    }

    @Test
    public void errosNoPreenchimentoDosCampos(){
        avenger.setApelido("Hom#em de Ferr$");
        avenger.setNomeReal("Tony St@rk");

        AvengerService avengerService =
                new AvengerService(avengerRepositoryPort, validacaoChain);

        ValidacaoException ex = Assertions.assertThrows(
                ValidacaoException.class,
                () -> avengerService.create(avenger)
        );

        Assertions.assertFalse(ex.getErrors().isEmpty());
        Mockito.verify(avengerRepositoryPort, Mockito.never())
                .create(Mockito.any());

    }
}
