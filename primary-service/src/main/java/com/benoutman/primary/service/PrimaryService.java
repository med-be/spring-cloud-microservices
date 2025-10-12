package com.benoutman.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benoutman.primary.feignclients.UnknownfeignClinet;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class PrimaryService {
	
	long count = 1;
	
	@Autowired
	private UnknownfeignClinet unknownService;
	
	@CircuitBreaker(name = "unknownService", fallbackMethod = "fallback")
	public String getUnknownService() {
		System.out.println("count = " + count);
		count++;
		return unknownService.getUnknown();
	}
	
	public String fallback(Throwable th) {
		System.out.println("Error = " + th);
		return "UNKNOWN SERVICE IS DOWN";
	}

}
