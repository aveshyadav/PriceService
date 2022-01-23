package com.witzeal.priceservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.witzeal.priceservice.dao.FastPriceDAOImpl;
import com.witzeal.priceservice.model.Product;
import com.witzeal.priceservice.service.impl.FastPriceServiceImpl;

@RestController
@RequestMapping("/app")
public class FastPriceController {

	private static Logger log = LoggerFactory.getLogger(FastPriceController.class);

	@Autowired
	private FastPriceServiceImpl fastPriceService;

	@Autowired
	private FastPriceDAOImpl fastPriceDao;

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

				CompletableFuture<Product> amazonPrice = fastPriceService.getFromAmazonPrice(productId);
				CompletableFuture<Product> flipkartPrice = fastPriceService.getFromFlipkartPrice(productId);

//	    		Wait until they are all done
				CompletableFuture.allOf(amazonPrice, flipkartPrice).join();

				log.info("AmazonPrice--> " + amazonPrice.get());
				log.info("FlipkartPrice--> " + amazonPrice.get());

				Product amazon = amazonPrice.get();
				Product flipkart = flipkartPrice.get();

				if (amazon.getProduct() == null && flipkart.getProduct() == null) {
					return ResponseEntity.notFound().build();
				}

				String source;
				if (amazon.getPrice() > flipkart.getPrice()) {
					map.put("price", amazon.getPrice());
					source = "amazon";
				} else {
					map.put("price", flipkart.getPrice());
					source = "flipkart";
				}

				long totalTime = System.currentTimeMillis() - startTime;
				fastPriceDao.insertFastestPrice(userId, productId, map.get("price"), totalTime, source);
				log.info("fastPriceAPI Completed");

				return ResponseEntity.ok().body(map);
			}
		};

	}

}
