package com.witzeal.priceservice.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fast_price")
public class FastPriceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "userid")
	private int userid;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "price")
	private long price;

	@Column(name = "add_current_time")
	private Date currentTime;

	@Column(name = "response_time")
	private long responseTime;

	@Column(name = "source")
	private String source;

	public FastPriceEntity() {

	}

	public FastPriceEntity(int userid, String productCode, long price, Date currentTime, long responseTime,
			String source) {
		this.userid = userid;
		this.productCode = productCode;
		this.price = price;
		this.currentTime = currentTime;
		this.responseTime = responseTime;
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}


	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
