package br.com.avengers.adapters.in.web;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import br.com.avengers.adapters.in.web.dto.DoisJogadoresDTO;
import br.com.avengers.domain.model.DoisJogadores;
import br.com.avengers.domain.model.JogoResponse;
import br.com.avengers.adapters.in.web.dto.UmJogadorDTO;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.model.UmJogador;
import br.com.avengers.ports.in.BatalhaResourcePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/batalha")
public class BatalhaResource {

    private final BatalhaResourcePort batalhaResourcePort;

    @Autowired
    public BatalhaResource(BatalhaResourcePort batalhaResourcePort) {
        this.batalhaResourcePort = batalhaResourcePort;
    }

    @GetMapping("/{protocoloId}")
    public ResponseEntity<BatalhaResultadoDTO> findById(@PathVariable String protocoloId) {
        // não da null pointer pois estou lançando a exceção no serviço caso não encontre
        // e usando GlobalExcpetionHandler no projeto
        return ResponseEntity.ok(batalhaResourcePort.findById(protocoloId));
    }

    @PostMapping("/um-jogador")
    public ResponseEntity<JogoResponse> umJogador(@RequestBody UmJogadorDTO umJogadorDTO) {
        JogoResponse jogoResponse = batalhaResourcePort.criarJogadaUmJogador(UmJogador.getInstanceFrom(umJogadorDTO));
        return ResponseEntity.ok(jogoResponse);
    }

    @PostMapping("/dois-jogadores")
    public ResponseEntity<JogoResponse> umJogador(@RequestBody DoisJogadoresDTO doisJogadoresDTO) {
        JogoResponse jogoResponse = batalhaResourcePort.criarJogadaDoisJogadores(DoisJogadores.getInstanceFrom(doisJogadoresDTO));
        return ResponseEntity.ok(jogoResponse);
    }



}
