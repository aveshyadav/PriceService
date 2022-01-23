package com.witzeal.priceservice.model;

import java.io.Serializable;

public class Product implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String product;
	private long price;
	
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [product=" + product + ", price=" + price + "]";
	}
}
