package br.com.avengers;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import br.com.avengers.domain.BatalhaService;
import br.com.avengers.domain.enums.ArtefatoEnum;
import br.com.avengers.domain.enums.TipoPersonagemEnum;
import br.com.avengers.domain.model.*;
import br.com.avengers.ports.out.*;
import br.com.avengers.shared.NegocioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BatalhaServiceTest {

    @Mock
    private CacheDBPort cacheDBPort;

    @Mock
    private AvengersClientPort avengersClientPort;

    @Mock
    private ViloesClientPort viloesClientPort;

    @Mock
    private MessagePort messagePort;

    @Mock
    private BatalhaRepositoryPort batalhaRepositoryPort;

    @InjectMocks
    private BatalhaService batalhaService;

    @Test
    void deveBuscarBatalhaPorProtocoloComSucesso() {
        String protocolo = "BATALHA123";

        BatalhaResultadoDTO dto = BatalhaResultadoDTO.builder()
                .protocolo(protocolo)
                .build();

        Mockito.when(batalhaRepositoryPort.buscarPorProtocolo(protocolo))
                .thenReturn(dto);

        BatalhaResultadoDTO resultado = batalhaService.findById(protocolo);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(protocolo, resultado.getProtocolo());
    }

    @Test
    void deveLancarExcecaoQuandoProtocoloNaoEncontrado() {
        String protocolo = "INVALIDO";

        Mockito.when(batalhaRepositoryPort.buscarPorProtocolo(protocolo))
                .thenReturn(null);

        NegocioException ex = Assertions.assertThrows(
                NegocioException.class,
                () -> batalhaService.findById(protocolo)
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void deveCriarJogadaUmJogadorEEnviarParaArena() {
        UmJogador request = new UmJogador(
                "Thor",
                TipoPersonagemEnum.HEROI,
                "Thanos",
                TipoPersonagemEnum.VILAO,
                ArtefatoEnum.MIJONIR,
                null
        );

        Mockito.when(avengersClientPort.buscarPorApelido("Thor"))
                .thenReturn(Personagem.builder().apelido(("Thor")).build());

        Mockito.when(viloesClientPort.buscarPorApelido("Thanos"))
                .thenReturn(Personagem.builder().apelido("Thanos").build());

        JogoResponse response = batalhaService.criarJogadaUmJogador(request);

        Assertions.assertTrue(response.getNumeroProtocolo().startsWith("BATALHA"));
        Assertions.assertEquals("Jogo começou", response.getMensagem());

        Mockito.verify(messagePort, Mockito.times(1))
                .enviarMensagem(ArgumentMatchers.eq("batalha"), ArgumentMatchers.any(Batalha.class));
    }

    @Test
    void deveCriarJogadaDoisJogadoresQuandoCacheExistir() {
        DoisJogadores cache = new DoisJogadores();
        cache.setIdJogo("JOGO1");
        cache.setProtocoloId("BATALHA123");
        cache.setTipoPersonagem(TipoPersonagemEnum.HEROI);
        cache.setApelidoLutador("Homem-Aranha");

        Mockito.when(cacheDBPort.buscarPorIdJogo("JOGO1"))
                .thenReturn(Optional.of(cache));

        Mockito.when(avengersClientPort.buscarPorApelido("Homem-Aranha"))
                .thenReturn(Personagem.builder().apelido("Homem-Aranha").build());

        Mockito.when(viloesClientPort.buscarPorApelido("Loki"))
                .thenReturn(Personagem.builder().apelido("Loki").build());

        DoisJogadores request = new DoisJogadores();
        request.setIdJogo("JOGO1");
        request.setTipoPersonagem(TipoPersonagemEnum.VILAO);
        request.setApelidoLutador("Loki");

        JogoResponse response = batalhaService.criarJogadaDoisJogadores(request);

        Assertions.assertEquals("BATALHA123", response.getNumeroProtocolo() );
        Assertions.assertEquals("Jogo começou", response.getMensagem());

        Mockito.verify(messagePort).enviarMensagem(ArgumentMatchers.eq("batalha"), ArgumentMatchers.any(Batalha.class));
        Mockito.verify(cacheDBPort).remover("JOGO1");
    }

    @Test
    void deveSalvarJogadorNoCacheQuandoNaoExistir() {
        Mockito.when(cacheDBPort.buscarPorIdJogo("JOGO2"))
                .thenReturn(Optional.empty());

        DoisJogadores request = new DoisJogadores();
        request.setIdJogo("JOGO2");
        request.setTipoPersonagem(TipoPersonagemEnum.HEROI);
        request.setApelidoLutador("Capitao America");

        JogoResponse response = batalhaService.criarJogadaDoisJogadores(request);

        Assertions.assertTrue(response.getNumeroProtocolo().startsWith("BATALHA"));
        Assertions.assertEquals("Aguardando o outro jogador...", response.getMensagem());

        Mockito.verify(cacheDBPort).salvar(ArgumentMatchers.eq("JOGO2"), ArgumentMatchers.any(DoisJogadores.class));
        Mockito.verify(messagePort, Mockito.never()).enviarMensagem(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

}
