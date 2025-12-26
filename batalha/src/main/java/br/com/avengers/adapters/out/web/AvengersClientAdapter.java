package br.com.avengers.adapters.out.web;

import br.com.avengers.adapters.dto.PersonagemDTO;
import br.com.avengers.domain.model.Personagem;
import br.com.avengers.ports.out.AvengersClientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AvengersClientAdapter implements AvengersClientPort {

    private final RestClient restClient;

    @Autowired
    public AvengersClientAdapter(@Qualifier("avengersRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Personagem buscarPorApelido(String apelido) {
        PersonagemDTO personagemDTO = restClient.get()
                .uri("/api/v1/avengers/personagem/{apelido}", apelido)
                .retrieve()
                .body(PersonagemDTO.class);
        return Personagem.getInstanceFrom(personagemDTO);
    }
}
