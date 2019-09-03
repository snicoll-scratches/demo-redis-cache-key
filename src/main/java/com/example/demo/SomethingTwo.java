package com.example.demo;

import java.util.Objects;
import java.util.StringJoiner;

public class SomethingTwo {
    private final String name;
    private final String hash;

    public SomethingTwo(String name, String hash) {
        this.name = name;
        this.hash = hash;
    }

    public String getName() {
        return this.name;
    }

    public String getHash() {
        return this.hash;
    }

    @Override
    public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
        SomethingTwo something = (SomethingTwo) o;
        return Objects.equals(name, something.name) && Objects.equals(hash, something.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hash);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("name = " + name)
                .add("hash = " + hash)
                .toString();
    }
}
