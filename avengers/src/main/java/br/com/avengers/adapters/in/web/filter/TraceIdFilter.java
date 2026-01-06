package br.com.avengers.adapters.in.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtra as chamadas para pegar o traceId e replicar.
 * Toda API deve conter.
 * Nesse projeto o TraceId é gerado no API Gateway.
 */
@Component
public class TraceIdFilter extends OncePerRequestFilter {

    private static final String HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String traceId = request.getHeader(HEADER);

        if (traceId != null) {
            ThreadContext.put("traceId", traceId);
            response.setHeader(HEADER, traceId);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            //ao final limpa porque as threads são reutilizadas
            ThreadContext.clearAll();
        }
    }
}
