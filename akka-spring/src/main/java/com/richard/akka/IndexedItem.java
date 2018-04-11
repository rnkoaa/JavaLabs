package com.richard.akka;

import java.io.Serializable;

public class IndexedItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String name;

	public String getName() {
		return name;
	}
	
	public IndexedItem(String name){
		this.name = name;
	}
	
	public String toString(){
		return "{name: " + getName() + " }";
	}

}
