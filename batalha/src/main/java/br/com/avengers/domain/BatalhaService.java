package br.com.avengers.domain;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import br.com.avengers.domain.enums.ArtefatoEnum;
import br.com.avengers.domain.enums.StatusEnum;
import br.com.avengers.domain.enums.TipoPersonagemEnum;
import br.com.avengers.domain.model.*;
import br.com.avengers.domain.validation.ValidaDoisJogadores;
import br.com.avengers.domain.validation.ValidaUmJogador;
import br.com.avengers.ports.in.BatalhaResourcePort;
import br.com.avengers.ports.out.*;
import br.com.avengers.shared.NegocioException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BatalhaService implements BatalhaResourcePort {

    private final CacheDBPort cacheDBPort;
    private final AvengersClientPort avengersClientPort;
    private final ViloesClientPort viloesClientPort;
    private final MessagePort messagePort;
    private final BatalhaRepositoryPort batalhaRepositoryPort;


    private final String topico = "batalha";

    @Autowired
    public BatalhaService(CacheDBPort cacheDBPort, AvengersClientPort avengersClientPort, ViloesClientPort viloesClientPort,
                          MessagePort messagePort, BatalhaRepositoryPort batalhaRepositoryPort) {
        this.cacheDBPort = cacheDBPort;
        this.avengersClientPort = avengersClientPort;
        this.viloesClientPort = viloesClientPort;
        this.messagePort = messagePort;
        this.batalhaRepositoryPort = batalhaRepositoryPort;
    }

    @Override
    public BatalhaResultadoDTO findById(String protocoloId) {
        BatalhaResultadoDTO batalhaResultadoDTO = batalhaRepositoryPort.buscarPorProtocolo(protocoloId);
        if(batalhaResultadoDTO == null) {
            throw new NegocioException("Protocolo não encontrado. Tente mais tarde ou verifique se está correto.", HttpStatus.BAD_REQUEST);
        }
        return batalhaResultadoDTO;
    }

    @Override
    public JogoResponse criarJogadaUmJogador(UmJogador umJogador) {

        ValidaUmJogador.valida(umJogador);

        log.info("Criando jogo");

        Batalha batalha = new Batalha();

        //gera protocolo
        String protocolo = GeradorProtocolo.gerarProtocolo();

        batalha.setProtocolo(protocolo);
        //busca personagem1 (webclient API avenger ou API viloes)
        batalha.setPersonagem1(preenchePersonagem(umJogador.getTipoPersonagem1(), umJogador.getApelidoLutador1(),
                umJogador.getArtefatoEnumJogador1()));
        //busca personagem2 (webclient API avenger ou API viloes)
        batalha.setPersonagem2(preenchePersonagem(umJogador.getTipoPersonagem2(), umJogador.getApelidoLutador2(),
                umJogador.getArtefatoEnumJogador2()));
        //envia objeto que vai para ARENA
        log.info("Enviando para a Arena");
        messagePort.enviarMensagem(topico, batalha);

        //retorna protocolo
        return new JogoResponse(protocolo, "Jogo começou");
    }

    @Override
    public JogoResponse criarJogadaDoisJogadores(DoisJogadores doisJogadores) {

        ValidaDoisJogadores.valida(doisJogadores);

        //consulta idJogo redis
        DoisJogadores doisJogadoresCache = cacheDBPort.buscarPorIdJogo(doisJogadores.getIdJogo()).orElse(null);
        if(doisJogadoresCache != null ) {
            log.info("Criando jogo");
            Batalha batalha = new Batalha();
            batalha.setProtocolo(doisJogadoresCache.getProtocoloId());
            //busca personagem1 (webclient API avenger ou API viloes)
            batalha.setPersonagem1(preenchePersonagem(doisJogadoresCache.getTipoPersonagem(), doisJogadoresCache.getApelidoLutador(),
                    doisJogadoresCache.getArtefatoEnumJogador()));
            //busca personagem2 (webclient API avenger ou API viloes)
            batalha.setPersonagem2(preenchePersonagem(doisJogadores.getTipoPersonagem(), doisJogadores.getApelidoLutador(),
                    doisJogadores.getArtefatoEnumJogador()));
            //Envia objeto para Arena
            log.info("Enviando para a Arena");
            messagePort.enviarMensagem(topico, batalha);
            //remove do cache
            cacheDBPort.remover(doisJogadoresCache.getIdJogo());
            //retorna protocolo - mensagem = "Jogo começou"
            return new JogoResponse(doisJogadoresCache.getProtocoloId(), "Jogo começou");
        } else {
            //se não existir gera e salva o protocolo no jogo
            String protocolo = GeradorProtocolo.gerarProtocolo();
            doisJogadores.setProtocoloId(protocolo);
            //salva objeto DoisJogadoresDTO no redis
            cacheDBPort.salvar(doisJogadores.getIdJogo(), doisJogadores);
            //retorna protocolo - mensagem = "Aguardando o outro jogador..."
            return new JogoResponse(protocolo, "Aguardando o outro jogador...");
        }
    }

    private String gerarNumeroProtocolo(){
        return GeradorProtocolo.gerarProtocolo();
    }

    private Personagem preenchePersonagem(TipoPersonagemEnum tipoPersonagemEnum, String apelido,
                                          ArtefatoEnum artefatoEnum) {

        Personagem personagem = (carregar(tipoPersonagemEnum, apelido));

        //atribui artefato (se houver)
        if(artefatoEnum != null) {
            personagem.setArtefato(
                    new Artefato(artefatoEnum,
                            artefatoEnum.getForcaAtaque(),
                            artefatoEnum.getForcaDefesa()));
        }
        //o status do cadastro se refere a situação no MCU, aqui fica ativo
        personagem.setStatusEnum(StatusEnum.ATIVO);
        return personagem;
    }

    private Personagem carregar(TipoPersonagemEnum tipoPersonagem, String apelido) {
        return tipoPersonagem.equals(TipoPersonagemEnum.HEROI) ? buscaHeroi(apelido) : buscaVilao(apelido);
    }

    private Personagem buscaHeroi(String apelido){
        return avengersClientPort.buscarPorApelido(apelido);
    }

    private Personagem buscaVilao(String apelido){
        return viloesClientPort.buscarPorApelido(apelido);
    }

}
