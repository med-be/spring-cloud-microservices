package com.benoutman.primary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.benoutman.primary.feignclients.UnknownfeignClinet;
import com.benoutman.primary.service.PrimaryService;

@RestController
@RequestMapping("/api/primary")
public class PrimaryController {
	
	@Autowired
	private PrimaryService primaryService;
	
	@GetMapping
	public String getPrimary() {
		System.out.println("Primary Service invoked !");
		return primaryService.getUnknownService();
	}

}
