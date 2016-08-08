package com.richard.akka;

import java.io.Serializable;
import java.util.List;

public class Indexable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<String> items;

	public Indexable(List<String> items) {
		this.items = items;
	}

	public List<String> getItems() {
		return items;
	}
}
