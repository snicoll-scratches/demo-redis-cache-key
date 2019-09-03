package com.example.demo;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
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
	public KeyGenerator customKeyGenerator() {
		return (target, method, params) -> new SimpleKey(params);
	}

	@Bean
	public RedisCacheConfiguration defaultRedisCacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
	}

	@Bean
	public ApplicationRunner runner(SomeService someService, CacheManager cacheManager) {
		return args -> {
			System.out.println("Clearing cache");
			cacheManager.getCache("something").clear();
			someService.findSomething(Collections.singletonList(new Something("test", "1")));
			someService.findSomething(Collections.singletonList(new Something("test", "1")));
			someService.findSomething(Arrays.asList(new Something("test", "1"), new Something("another", "2")));
			someService.findSomething(Collections.singletonList(new Something("test", "1")));
			someService.findSomething(Collections.singletonList(new Something("test", "1")));
		};
	}

	//@Bean
	public ApplicationRunner runner2(SomeService someService, CacheManager cacheManager) {
		return args -> {
			System.out.println("Clearing cache");
			cacheManager.getCache("something").clear();
			someService.findSomething2(Collections.singletonList(new Something("test", "1")));
			someService.findSomething2(Collections.singletonList(new Something("test", "1")));
			someService.findSomething2(Arrays.asList(new Something("test", "1"), new Something("another", "2")));
			someService.findSomething2(Collections.singletonList(new Something("test", "1")));
			someService.findSomething2(Collections.singletonList(new Something("test", "1")));
		};
	}

}
