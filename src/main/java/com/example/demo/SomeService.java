package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stephane Nicoll
 */
@Service
@CacheConfig(cacheNames = "something")
public class SomeService {

	@Cacheable
	public String findSomething(List<Something> somethings) {
		String result = somethings.stream().map(Something::getName).collect(Collectors.joining(", "));
		System.out.println("Called and generated " + result);
		return result;
	}

}
