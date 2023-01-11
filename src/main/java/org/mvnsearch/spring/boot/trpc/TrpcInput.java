package org.mvnsearch.spring.boot.trpc;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TrpcInput {
}
