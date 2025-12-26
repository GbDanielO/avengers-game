package br.com.avengers.domain;

import br.com.avengers.domain.enums.ArtefatoEnum;
import br.com.avengers.domain.enums.TipoPersonagemEnum;
import br.com.avengers.domain.model.*;
import br.com.avengers.ports.in.BatalhaResourcePort;
import br.com.avengers.ports.out.AvengersClientPort;
import br.com.avengers.ports.out.CacheDBPort;
import br.com.avengers.ports.out.ViloesClientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatalhaService implements BatalhaResourcePort {

    private final CacheDBPort cacheDBPort;
    private final AvengersClientPort avengersClientPort;
    private final ViloesClientPort viloesClientPort;

    @Autowired
    public BatalhaService(CacheDBPort cacheDBPort, AvengersClientPort avengersClientPort, ViloesClientPort viloesClientPort) {
        this.cacheDBPort = cacheDBPort;
        this.avengersClientPort = avengersClientPort;
        this.viloesClientPort = viloesClientPort;
    }

    //TODO vai buscar no MongoDB
    @Override
    public Batalha findById(String protocoloId) {
        return null;
    }

    @Override
    public JogoResponse criarJogadaUmJogador(UmJogador umJogador) {
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

        //retorna protocolo
        return new JogoResponse(protocolo, "Jogo começou");
    }

    @Override
    public JogoResponse criarJogadaDoisJogadores(DoisJogadores doisJogadores) {

        //consulta idJogo redis
        DoisJogadores doisJogadoresCache = cacheDBPort.buscarPorIdJogo(doisJogadores.getIdJogo()).orElse(null);
        if(doisJogadoresCache != null ) {
            Batalha batalha = new Batalha();
            batalha.setProtocolo(doisJogadoresCache.getProtocoloId());
            //busca personagem1 (webclient API avenger ou API viloes)
            batalha.setPersonagem1(preenchePersonagem(doisJogadoresCache.getTipoPersonagem(), doisJogadoresCache.getApelidoLutador(),
                    doisJogadoresCache.getArtefatoEnumJogador()));
            //busca personagem2 (webclient API avenger ou API viloes)
            batalha.setPersonagem2(preenchePersonagem(doisJogadores.getTipoPersonagem(), doisJogadores.getApelidoLutador(),
                    doisJogadores.getArtefatoEnumJogador()));
            //Envia objeto para Arena
            //TODO
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
