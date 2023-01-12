package org.mvnsearch.spring.boot.trpc.flux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mvnsearch.spring.boot.trpc.TrpcInput;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


public class TrpcInputArgumentResolver extends AbstractMessageReaderArgumentResolver {
    private final ObjectMapper objectMapper;

    public TrpcInputArgumentResolver(List<HttpMessageReader<?>> readers, ReactiveAdapterRegistry registry, ObjectMapper objectMapper) {
        super(readers, registry);
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TrpcInput.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        if (exchange.getRequest().getMethod() == HttpMethod.POST) {
            return this.readBody(parameter, true, bindingContext, exchange);
        } else {
            return Mono.create(monoSink -> {
                try {
                    String input = exchange.getRequest().getQueryParams().getFirst("input");
                    if (input == null) {
                        monoSink.error(new Exception("input parameter is required!"));
                    } else {
                        monoSink.success(objectMapper.readValue(input, parameter.getParameterType()));
                    }
                } catch (Exception e) {
                    monoSink.error(e);
                }
            });
        }
    }
}
