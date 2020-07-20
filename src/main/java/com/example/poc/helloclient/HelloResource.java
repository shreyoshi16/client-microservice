package com.example.poc.helloclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RestController
@RequestMapping("rest/hello/client")
public class HelloResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "fallback", commandProperties = {
			   @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000000")
			})
	@GetMapping
	public String hello() throws InterruptedException {
		//Thread.sleep(3000);
		String url = "http://hello-server/rest/hello/server"; //"http://localhost:8071/rest/hello/server" http://localhost:8072/rest/hello/client
		return restTemplate.getForObject(url, String.class);
	}
	
	public String fallback() {
		return "I am in fallback - Request expired";		
	}
	
}
