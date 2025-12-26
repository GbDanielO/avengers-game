package br.com.avengers.adapters.out.persistence;

import br.com.avengers.domain.model.DoisJogadores;
import br.com.avengers.ports.out.CacheDBPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class CacheDBAdapter implements CacheDBPort {

    private static final String PREFIX = "batalha:jogo:";

    @Value("${jogo.tempo-max-espera}")
    private Integer tempo;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CacheDBAdapter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void salvar(String idJogo, DoisJogadores jogo) {
        redisTemplate.opsForValue()
                .set(PREFIX + idJogo, jogo, Duration.ofSeconds(tempo));
    }

    @Override
    public Optional<DoisJogadores> buscarPorIdJogo(String idJogo) {
        Object obj = redisTemplate.opsForValue().get(PREFIX + idJogo);
        return Optional.ofNullable((DoisJogadores) obj);
    }

    @Override
    public void remover(String idJogo) {
        redisTemplate.delete(PREFIX + idJogo);
    }
}
