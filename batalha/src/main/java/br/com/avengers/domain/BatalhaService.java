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
        return new JogoResponse(protocolo, "Jogo iniciado");
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

    @Override
    public JogoResponse criarJogadaDoisJogadores(DoisJogadores doisJogadores) {

        //consulta idJogo redis
        //se existir
                //traz objeto DoisJogadoresDTO do redis
                //busca personagem1 (webclient API avenger ou API viloes)
                //atribui artefato (se houver)
                //busca personagem2 (webclient API avenger ou API viloes)
                //atribui artefato (se houver)
                //monta objeto que vai para ARENA e envia via kafka
                //retorna protocolo - mensagem = "Jogo começou"
        //se não existir
                //salva objeto DoisJogadoresDTO no redis
                //retorna protocolo - mensagem = "Aguardando o outro jogador..."
        return null;
    }

    private String gerarNumeroProtocolo(){
        return GeradorProtocolo.gerarProtocolo();
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
