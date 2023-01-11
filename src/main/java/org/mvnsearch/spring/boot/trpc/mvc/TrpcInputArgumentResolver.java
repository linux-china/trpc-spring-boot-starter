package org.mvnsearch.spring.boot.trpc.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mvnsearch.spring.boot.trpc.TrpcInput;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


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
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String input = webRequest.getParameter("input");
        if (input != null) {  //tRPC query
            return objectMapper.readValue(input, parameter.getParameterType());
        }
        return null;
    }

}
