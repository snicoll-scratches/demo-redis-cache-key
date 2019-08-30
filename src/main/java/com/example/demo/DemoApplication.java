package com.example.demo;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootApplication
@EnableCaching
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RedisCacheConfiguration defaultRedisCacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
	}

	@Bean
	public ApplicationRunner runner(SomeService someService) {
		return args -> {
			someService.findSomething(Collections.singletonList(new Something("test")));
			someService.findSomething(Collections.singletonList(new Something("test")));
			someService.findSomething(Arrays.asList(new Something("test"), new Something("another")));
			someService.findSomething(Collections.singletonList(new Something("test")));
			someService.findSomething(Collections.singletonList(new Something("test")));
		};
	}

}
