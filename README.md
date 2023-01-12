Spring Boot Starter tRPC
=============================================

Spring Boot starter for [tRPC](https://trpc.io/)

# Get Started

* Add dependency in your pom.xml

```xml

<dependency>
    <groupId>org.mvnsearch</groupId>
    <artifactId>trpc-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
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

### References

* tRPC: https://trpc.io/