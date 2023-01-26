Spring Boot Starter tRPC
=============================================

Spring Boot starter for [tRPC](https://trpc.io/), and compatible with webmvc/webflux of Spring Boot 2.x/3.0.

# Get Started

* Add dependency in your pom.xml

```xml

<dependency>
    <groupId>org.mvnsearch</groupId>
    <artifactId>trpc-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

* Write controller for tRPC query/mutate

```java

import org.mvnsearch.spring.boot.trpc.TrpcInput;
import org.mvnsearch.spring.boot.trpc.TrpcMutate;
import org.mvnsearch.spring.boot.trpc.TrpcQuery;
import org.mvnsearch.spring.boot.trpc.TrpcResponse;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class TrpcHelloController {
    record Hello(String name) {
    }

    @TrpcQuery("/greeting.hello")
    public TrpcResponse<String> hello(@TrpcInput Hello hello) {
        return TrpcResponse.of("Hello " + hello.name);
    }

    record Poster(String id, String title, String text) {
    }

    @TrpcMutate("/poster.createPost")
    public TrpcResponse<Poster> createPost(@TrpcInput Poster poster) {
        return TrpcResponse.of(new Poster(UUID.randomUUID().toString(), poster.title, poster.text));
    }
}
```

* Test your tRPC service with [httpx](https://httpx.sh/docs/tutorial-basics/trpc-testing)

```
### trpc query
TRPC http://localhost:8080/greeting.hello
Content-Type: application/json

{
  "name": "world"
}

### trpc mutate
TRPCM http://localhost:8080/poster.createPost
Content-Type: application/json

{
  "title": "title",
  "text": "hello world"
}
```
      
# FAQ

### Could I use Spring Framework 6 HTTP Interface with tRPC?
             
Please refer to [tRPC Spring HTTP Interface](https://github.com/linux-china/trpc-http-interface)

# References

* tRPC: https://trpc.io/
* tRPC testing: https://servicex.sh/docs/tutorial-basics/trpc-testing
* tRPC Spring HTTP Interface: https://github.com/linux-china/trpc-http-interface