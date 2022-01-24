package com.witzeal.priceservice.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.witzeal.priceservice.dao.FastPriceDAOImpl;
import com.witzeal.priceservice.dao.ProductRepository;
import com.witzeal.priceservice.jpa.entity.FastPriceEntity;
import com.witzeal.priceservice.model.Product;
import com.witzeal.priceservice.service.impl.FastPriceServiceImpl;

@RestController
@RequestMapping("/app")
public class FastPriceController {

	private static Logger log = LoggerFactory.getLogger(FastPriceController.class);

	@Autowired
	private FastPriceServiceImpl fastPriceService;

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/{productId}/price")
	public Callable<ResponseEntity<?>> fastPrice(@PathVariable String productId, HttpServletRequest request)
			throws InterruptedException, ExecutionException {
		log.info("fastPriceAPI Start");

		long startTime = System.currentTimeMillis();

		Map<String, Long> map = new HashMap<String, Long>();
		String userId = request.getHeader("X-User");

		return new Callable<ResponseEntity<?>>() {
			@Override
			public ResponseEntity<?> call() throws Exception {

				if (userId == null || userId.equals("")) {
					return ResponseEntity.badRequest().build();
				}

				Future<Product> amazonPrice = fastPriceService.getFromAmazonPrice(productId);
				Future<Product> flipkartPrice = fastPriceService.getFromFlipkartPrice(productId);
				
				long totalTime = 0;
				String source;
				while(true) {
					if(amazonPrice.isDone()) {
						map.put("price", amazonPrice.get().getPrice());
						source = "amazon";
						totalTime = System.currentTimeMillis() - startTime;
						break;
					}
					else if(flipkartPrice.isDone()) {
						map.put("price", flipkartPrice.get().getPrice());
						source = "flipkart";
						totalTime = System.currentTimeMillis() - startTime;
						break;
					}
				}
				
				
				FastPriceEntity fastPrice = new FastPriceEntity(Integer.parseInt(userId), productId, map.get("price"), new Date(), totalTime, source);
				
				productRepository.save(fastPrice);
				log.info("fastPriceAPI Completed");

				return ResponseEntity.ok().body(map);
			}
		};

	}

}
