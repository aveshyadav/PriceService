package com.witzeal.priceservice.model;

import java.io.Serializable;

public class Product implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String product;
	private long price;
	private String source;
	
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "Product [product=" + product + ", price=" + price + ", source=" + source + "]";
	}
}
