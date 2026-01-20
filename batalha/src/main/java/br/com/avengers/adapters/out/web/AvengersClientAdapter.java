package br.com.avengers.adapters.out.web;

import br.com.avengers.adapters.dto.PersonagemDTO;
import br.com.avengers.domain.model.Personagem;
import br.com.avengers.ports.out.AvengersClientPort;
import br.com.avengers.shared.NegocioException;
import br.com.avengers.shared.ResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AvengersClientAdapter implements AvengersClientPort {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public AvengersClientAdapter(@Qualifier("avengersRestClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Personagem buscarPorApelido(String apelido) {

        PersonagemDTO personagemDTO = restClient.get()
                .uri("/api/v1/avengers/personagem/{apelido}", apelido)
                .header("X-Trace-Id", ThreadContext.get("traceId"))
                .retrieve()
                .onStatus(
                        status -> status.value() == 400,
                        (request, response) -> {

                            ResponseError erro = objectMapper.readValue(response.getBody(), ResponseError.class);
                            String mensagem = erro != null
                                    ? erro.getMensagem()
                                    : "Personagem não encontrado";

                            throw new NegocioException(mensagem);
                        }
                )
                .body(PersonagemDTO.class);

        return Personagem.getInstanceFrom(personagemDTO);

    }
}
