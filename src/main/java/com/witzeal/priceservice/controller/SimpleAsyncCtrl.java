package com.witzeal.priceservice.controller;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

@RestController
public class SimpleAsyncCtrl {

	private static Logger log = LoggerFactory.getLogger(SimpleAsyncCtrl.class);
	
	
	@GetMapping(path = "/api/v1/task")
	public WebAsyncTask<Map<String, Object>> simpleAsyncTask(@RequestParam(defaultValue = "5") long t) {

		log.info("Method starts");
		return new WebAsyncTask<Map<String, Object>>(10000, () -> {
			log.info("Method Processing");
			Thread.sleep(t * 1000);
			log.info("Method Ends");
			return Collections.<String, Object>singletonMap("key", "success");
		});
		
	}
	
	
}