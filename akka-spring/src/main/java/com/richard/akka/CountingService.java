package com.richard.akka;

import org.springframework.stereotype.Component;

@Component
public class CountingService {
	/**
	   * Increment the given number by one.
	   */
	  public int increment(int count) {
	    return count + 1;
	  }
}
