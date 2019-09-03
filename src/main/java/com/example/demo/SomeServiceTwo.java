package com.example.demo;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "something-two")
public class SomeServiceTwo {

    @Cacheable
    public String findSomething(List<SomethingTwo> somethings) {
        String result = somethings.stream().map(SomethingTwo::getName).collect(Collectors.joining(", "));
        System.out.println("Called and generated " + result);
        return result;
    }

}
