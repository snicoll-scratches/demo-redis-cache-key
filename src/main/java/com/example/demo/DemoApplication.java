package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@EnableCaching
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                                      .entryTtl(Duration.ofSeconds(5))
                                      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
    }

    /**
     * Same as {@link SimpleKeyGenerator} but handles param types in the Collection or Map interface hierarchy.
     * <p>
     * Should be used if a {@link Cacheable} method has a single argument that belongs to the Collection or Map interface hierarchy.
     * <p>
     * {@link SimpleKeyGenerator} returns the raw param as the generated key, which more than likely cannot
     * be serialized into a string. This will return a {@link SimpleKey} constructed with the param instead.
     */
    @Bean
    public KeyGenerator collectionAndMapAwareKeyGenerator() {
        return (target, method, params) -> {
            if (params.length == 0) {
                return SimpleKey.EMPTY;
            }
            if (params.length == 1) {
                final Object param = params[0];
                if (param != null && !param.getClass().isArray()
                        // this is the part that differs from SimpleKeyGenerator
                        && !Collection.class.isAssignableFrom(param.getClass())
                        && !Map.class.isAssignableFrom(param.getClass())) {
                    return param;
                }
            }
            return new SimpleKey(params);
        };
    }

    @Bean
    public ApplicationRunner runner(SomeService someService, SomeServiceTwo someServiceTwo, SomeServiceTwoWithKeyGenerator someServiceTwoWithKeyGenerator) {
        return args -> {
            // works
            someService.findSomething(Collections.singletonList(new Something("test")));
            someService.findSomething(Collections.singletonList(new Something("test")));
            someService.findSomething(Arrays.asList(new Something("test"), new Something("another")));
            someService.findSomething(Collections.singletonList(new Something("test")));
            someService.findSomething(Collections.singletonList(new Something("test")));

            // works
            someServiceTwoWithKeyGenerator.findSomething(Collections.singletonList(new SomethingTwo("test2keygen", "hash")));
            someServiceTwoWithKeyGenerator.findSomething(Collections.singletonList(new SomethingTwo("test2keygen", "hash")));
            someServiceTwoWithKeyGenerator.findSomething(Arrays.asList(new SomethingTwo("test2keygen", "hash"), new SomethingTwo("another2keygen", "hash")));
            someServiceTwoWithKeyGenerator.findSomething(Collections.singletonList(new SomethingTwo("test2keygen", "hash")));
            someServiceTwoWithKeyGenerator.findSomething(Collections.singletonList(new SomethingTwo("test2keygen", "hash")));

            // fails
            someServiceTwo.findSomething(Collections.singletonList(new SomethingTwo("test2", "hash")));
            someServiceTwo.findSomething(Collections.singletonList(new SomethingTwo("test2", "hash")));
            someServiceTwo.findSomething(Arrays.asList(new SomethingTwo("test2", "hash"), new SomethingTwo("another2", "hash")));
            someServiceTwo.findSomething(Collections.singletonList(new SomethingTwo("test2", "hash")));
            someServiceTwo.findSomething(Collections.singletonList(new SomethingTwo("test2", "hash")));
        };
    }

}
