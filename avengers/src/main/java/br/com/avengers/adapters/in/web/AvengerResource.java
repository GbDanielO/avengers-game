package br.com.avengers.adapters.in.web;

import br.com.avengers.adapters.dto.AvengerDTO;
import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.ports.in.AvengerResourcePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/avengers")
public class AvengerResource{

    private final AvengerResourcePort avengerResourcePort;

    @Autowired
    public AvengerResource(AvengerResourcePort avengerResourcePort) {
        this.avengerResourcePort = avengerResourcePort;
    }

    @GetMapping
    public ResponseEntity<List<AvengerDTO>> findAll() {
        return ResponseEntity.ok(avengerResourcePort.findAll()
                .stream().map(AvengerDTO::getInstanceFrom).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvengerDTO> findById(@PathVariable Long id) {
        // não da null pointer pois estou lançando a exceção no serviço caso não encontre
        // e usando GlobalExcpetionHandler no projeto
        return ResponseEntity.ok(AvengerDTO.getInstanceFrom(avengerResourcePort.findById(id)));
    }

    @GetMapping("/{apelido}")
    public ResponseEntity<AvengerDTO> findByApelido(@PathVariable String apelido){
        return ResponseEntity.ok(AvengerDTO.getInstanceFrom(avengerResourcePort.findByApelido(apelido)));
    }

    @PostMapping
    public ResponseEntity<AvengerDTO> create(@RequestBody AvengerDTO avenger) {
        Avenger created = avengerResourcePort.create(Avenger.getInstanceFrom(avenger));
        return ResponseEntity.status(HttpStatus.CREATED).body(AvengerDTO.getInstanceFrom(created));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<AvengerDTO>> create(@RequestBody List<AvengerDTO> avengers) {
        List<Avenger> result = avengerResourcePort.create(avengers.stream().map(Avenger::getInstanceFrom).toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(result.stream().map(AvengerDTO::getInstanceFrom).toList());
    }

    @PutMapping()
    public ResponseEntity<AvengerDTO> update(@RequestBody AvengerDTO avenger) {
        Avenger updated = avengerResourcePort.update(Avenger.getInstanceFrom(avenger));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(AvengerDTO.getInstanceFrom(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        avengerResourcePort.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
