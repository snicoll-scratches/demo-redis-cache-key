package com.example.demo;

import java.util.Objects;

/**
 *
 * @author Stephane Nicoll
 */
public class Something {

	private final String name;

	private final String test;

	public Something(String name, String test) {
		this.name = name;
		this.test = test;
	}

	public String getName() {
		return this.name;
	}

	public String getTest() {
		return this.test;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Something something = (Something) o;
		return Objects.equals(name, something.name) &&
				Objects.equals(test, something.test);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, test);
	}
}
