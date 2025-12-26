package br.com.avengers.config;

import br.com.avengers.domain.model.DoisJogadores;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * Para mais de um template, use nome específico para o método,
     * por exemplo cacheDoisJogadores ao invés de redisTemplate,
     * assim crie os beans necessários e na hora de injetar use
     * o @Qualifier com o nome do método: @Qualifier("cacheDoisJogadores")
     * @param connectionFactory
     * @param objectMapper
     * @return RedisTemplate<String, DoisJogadores>
     */
    @Bean
    public RedisTemplate<String, DoisJogadores> redisTemplate(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper
    ) {
        RedisTemplate<String, DoisJogadores> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<DoisJogadores> serializer =
                new Jackson2JsonRedisSerializer<>(DoisJogadores.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        return template;
    }
}
