package br.com.avengers.adapters.out.web.intercept;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class TraceIdInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        String traceId = ThreadContext.get("traceId");
        if (traceId != null) {
            request.getHeaders().add("X-Trace-Id", traceId);
        }

        return execution.execute(request, body);
    }
}
