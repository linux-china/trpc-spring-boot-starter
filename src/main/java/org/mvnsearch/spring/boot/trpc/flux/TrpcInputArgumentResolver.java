package org.mvnsearch.spring.boot.trpc.flux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mvnsearch.spring.boot.trpc.TrpcInput;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class TrpcInputArgumentResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper objectMapper;

    public TrpcInputArgumentResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TrpcInput.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        if (exchange.getRequest().getMethod() == HttpMethod.GET) {  //tRPC mutate
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
        return null;
    }

}
