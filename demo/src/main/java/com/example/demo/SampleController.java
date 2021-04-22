package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@RequestMapping(value = "/get/v1", produces = "application/json")
	public String getSample() {
		System.out.println("Hello");
		return "{\"sagar\":\"patil\"}";
	}
}
