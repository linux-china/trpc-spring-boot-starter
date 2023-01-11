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

* Write controller for tRPC query

```java

@RestController
public class GreetingTrpcController {
    record Hello(String name) {
    }

    @GetMapping("/greeting.hello")
    public TrpcResponse<String> hello(@TrpcInput Hello hello) {
        return TrpcResponse.of("Hello " + hello.name);
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
```

# FAQ

### `@TrpcInput` could be used as tRPC mutate?

Please use `@RequestBody` instead of `@TrpcInput` for tRPC mutate, and `@RequestBody` is a standard and works well with tRPC input JSON data with POST.

```java

@RestController
public class PosterTrpcController {
    record Poster(@Nullable String id, String title, String text) {
    }

    @PostMapping("/poster.createPost")
    public TrpcResponse<Poster> createPost(@RequestBody Poster poster) {
        return TrpcResponse.of(new Poster(UUID.randomUUID().toString(), poster.title, poster.text));
    }
}
```

### References

* tRPC: https://trpc.io/