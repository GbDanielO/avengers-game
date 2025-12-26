package br.com.avengers.adapters.in.web;

import br.com.avengers.adapters.dto.ViloesDTO;
import br.com.avengers.adapters.out.persistence.entity.Vilao;
import br.com.avengers.ports.in.ViloesResourcePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/viloes")
public class ViloesResource {

    private final ViloesResourcePort viloesResourcePort;

    @Autowired
    public ViloesResource(ViloesResourcePort viloesResourcePort) {
        this.viloesResourcePort = viloesResourcePort;
    }

    @GetMapping
    public ResponseEntity<List<ViloesDTO>> findAll() {
        return ResponseEntity.ok(viloesResourcePort.findAll()
                .stream().map(ViloesDTO::getInstanceFrom).toList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ViloesDTO> findById(@PathVariable Long id) {
        // não da null pointer pois estou lançando a exceção no serviço caso não encontre
        // e usando GlobalExcpetionHandler no projeto
        return ResponseEntity.ok(ViloesDTO.getInstanceFrom(viloesResourcePort.findById(id)));
    }

    @GetMapping("/personagem/{apelido}")
    public ResponseEntity<ViloesDTO> findByApelido(@PathVariable String apelido) {
        // não da null pointer pois estou lançando a exceção no serviço caso não encontre
        // e usando GlobalExcpetionHandler no projeto
        return ResponseEntity.ok(ViloesDTO.getInstanceFrom(viloesResourcePort.findByApelido(apelido)));
    }

    @PostMapping
    public ResponseEntity<ViloesDTO> create(@RequestBody ViloesDTO vilao) {
        Vilao created = viloesResourcePort.create(Vilao.getInstanceFrom(vilao));
        return ResponseEntity.status(HttpStatus.CREATED).body(ViloesDTO.getInstanceFrom(created));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<ViloesDTO>> create(@RequestBody List<ViloesDTO> viloes) {
        List<Vilao> viloesSalvos = viloesResourcePort.create(viloes.stream().map(Vilao::getInstanceFrom).toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(viloesSalvos.stream().map(ViloesDTO::getInstanceFrom).toList());
    }

    @PutMapping()
    public ResponseEntity<ViloesDTO> update(@RequestBody ViloesDTO vilao) {
        Vilao updated = viloesResourcePort.update(Vilao.getInstanceFrom(vilao));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ViloesDTO.getInstanceFrom(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        viloesResourcePort.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
