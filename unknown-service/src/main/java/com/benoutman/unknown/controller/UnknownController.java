package com.benoutman.unknown.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unknown")
public class UnknownController {
	
	@GetMapping
	public String getUnknown() {
		System.out.println("getUnknown invoked !");
		return "Unknown Service";
	}
	

}
