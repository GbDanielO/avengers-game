package br.com.avengers.filters;

import br.com.avengers.utils.GeradorTraceId;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * A ideia é esse filtro passar por todas as requisições e aparecer nos logs
 * e ser salvo no banco. É uma forma de mapear uma requisição de ponta a ponta.
 */
@Component
public class TraceIdFilter implements GlobalFilter {

    private static final String HEADER_NAME = "X-Trace-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String traceId = exchange.getRequest()
                .getHeaders()
                .getFirst(HEADER_NAME);

        if (traceId == null) {
            traceId = GeradorTraceId.gerarTraceID();
        }

        var request = exchange.getRequest()
                .mutate()
                .header(HEADER_NAME, traceId)
                .build();

        return chain.filter(exchange.mutate().request(request).build());
    }
}
