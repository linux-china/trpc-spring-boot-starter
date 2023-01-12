package org.mvnsearch.spring.boot.trpc.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mvnsearch.spring.boot.trpc.TrpcInput;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

public class TrpcInputArgumentResolver extends RequestResponseBodyMethodProcessor {
    private ObjectMapper objectMapper;

    public TrpcInputArgumentResolver(List<HttpMessageConverter<?>> converters, ObjectMapper objectMapper) {
        super(converters);
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TrpcInput.class);
    }

    protected boolean checkRequired(MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String input = webRequest.getParameter("input");
        if (input != null) {  //tRPC query
            return objectMapper.readValue(input, parameter.getParameterType());
        } else {
            return super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        }
    }
}
