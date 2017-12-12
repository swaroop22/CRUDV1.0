package com.paragon;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloController {
    
	@RequestMapping(value = "hello", method = RequestMethod.GET)
    public String index() {
        return "Greetings from Spring Boot!";
    }
	
	@RequestMapping(value = "hello", method = RequestMethod.POST)
    public String indexPost(@RequestBody Object obj) {
        return "Greetings from Spring Boot Post!";
    }
    
}
