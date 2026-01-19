package br.com.avengers.domain;

import br.com.avengers.domain.enums.StatusEnum;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.model.Habilidade;
import br.com.avengers.domain.model.Personagem;
import br.com.avengers.domain.validation.ValidaHabilidade;
import br.com.avengers.domain.validation.ValidaPersonagem;
import br.com.avengers.domain.validation.ValidacaoChain;
import br.com.avengers.ports.out.ArenaRepositoryPort;
import br.com.avengers.shared.NegocioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class ArenaServiceTest {

    @Mock
    private ValidacaoChain validacaoChain;

    private Personagem personagem;

    @Mock
    private ArenaEngine arenaEngine;

    @Mock
    private ArenaRepositoryPort arenaRepositoryPort;

    @InjectMocks
    private ArenaService arenaService;

    @BeforeEach
    public void montaObjetos(){
        validacaoChain = new ValidacaoChain(new ValidaPersonagem(), new ValidaHabilidade());

        personagem = Personagem.builder()
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
    void deveExecutarBatalha() {

        String traceId = "AVENGERSGAMETESTE";

        Batalha batalha = new Batalha();
        batalha.setProtocolo("BATALHA123");
        batalha.setPersonagem1(personagem);
        batalha.setPersonagem2(personagem);

        Mockito.when(arenaEngine.execute(batalha))
                .thenReturn(batalha);

        new ArenaService(validacaoChain, arenaEngine, arenaRepositoryPort).processaBatalha(batalha, traceId);

        Mockito.verify(arenaEngine, Mockito.times(1)).execute(batalha);
        Mockito.verify(arenaRepositoryPort, Mockito.times(1)).salvar(batalha);
    }

    /**
     * Aqui ele falha ao executar a batalha, porém não lança a exceção
     * porque é uma exceção de negócio que chegou através de uma mensagem
     * Kafka. O que ele faz é logar o erro e daí cada sistema deve tomar
     * uma atitude como salvar no banco, ou enviar uma outra mensagem
     * via kafka pra informar a falha... depende do negócio.
     */
    @Test
    void deveFalharAoExecutarBatalha() {

        String traceId = "AVENGERSGAMETESTE-FALHA";

        Batalha batalha = new Batalha();
        batalha.setProtocolo("BATALHA123");
        batalha.setPersonagem1(personagem);

        new ArenaService(validacaoChain, arenaEngine, arenaRepositoryPort).processaBatalha(batalha, traceId);

        Mockito.verify(arenaEngine, Mockito.never()).execute(batalha);
        Mockito.verify(arenaRepositoryPort, Mockito.never()).salvar(batalha);
    }

}
