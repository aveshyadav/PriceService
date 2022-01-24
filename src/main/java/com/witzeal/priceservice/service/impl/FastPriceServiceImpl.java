package com.witzeal.priceservice.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.witzeal.priceservice.model.Product;

@Service
public class FastPriceServiceImpl {

	private static Logger log = LoggerFactory.getLogger(FastPriceServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Async("asyncExecutor")
	public CompletableFuture<Product> getFromAmazonPrice(String product) 
	{
		log.info("getFromAmazonPrice Starts");
		
		Product productPrice = new Product();
		try {
			productPrice = restTemplate.getForObject("https://price-amazon.free.beeceptor.com/service/"+product+"/price", Product.class);

			productPrice.setSource("amazon");
			log.info("productDetails, {}", productPrice);
			log.info("getFromAmazonPrice completed");
		}
		catch(RestClientException ex) {
			log.error(ex.getMessage());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return CompletableFuture.completedFuture(productPrice);
	}
	
	
	@Async("asyncExecutor")
	public CompletableFuture<Product> getFromFlipkartPrice(String product) 
	{
		
		log.info("getFromFlipkartPrice Starts");
		Product productPrice = new Product();
		try {
			productPrice = restTemplate.getForObject("https://price-flipkart.free.beeceptor.com/service/"+product+"/price", Product.class);

			productPrice.setSource("flipkart");
			log.info("productDetails, {}", productPrice);
			log.info("getFromFlipkartPrice completed");
		}
		catch(RestClientException ex) {
			log.error(ex.getMessage());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return CompletableFuture.completedFuture(productPrice);
	}
	
}
