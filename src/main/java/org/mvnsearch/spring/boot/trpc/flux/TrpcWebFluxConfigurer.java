package org.mvnsearch.spring.boot.trpc.flux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

import java.util.List;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class TrpcWebFluxConfigurer implements WebFluxConfigurer {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private ReactiveAdapterRegistry registry;
    private List<HttpMessageReader<?>> readers;

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new TrpcInputArgumentResolver(this.readers, this.registry, this.objectMapper));
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        this.readers = configurer.getReaders();
    }

}
