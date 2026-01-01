package br.com.avengers.domain;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.out.ArenaRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArenaServiceTest {
    @Mock
    private ArenaEngine arenaEngine;

    @Mock
    private ArenaRepositoryPort arenaRepositoryPort;

    @InjectMocks
    private ArenaService arenaService;

    @Test
    void deveExecutarBatalhaESalvarResultado() {
        // arrange
        Batalha batalhaEntrada = Mockito.mock(Batalha.class);
        Batalha batalhaFinalizada = Mockito.mock(Batalha.class);

        Mockito.when(arenaEngine.execute(batalhaEntrada))
                .thenReturn(batalhaFinalizada);

        // act
        arenaService.processaBatalha(batalhaEntrada);

        // assert
        Mockito.verify(arenaEngine, Mockito.times(1)).execute(batalhaEntrada);
        Mockito.verify(arenaRepositoryPort, Mockito.times(1)).salvar(batalhaFinalizada);
    }
}
