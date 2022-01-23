package com.witzeal.priceservice.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FastPriceDAOImpl {

	private static Logger log = LoggerFactory.getLogger(FastPriceDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int insertFastestPrice(String userId, String productCode, long price, long responseTime, String source) {

		try {
			Timestamp currentDate = new Timestamp(new Date().getTime());

			String sql = "insert into fast_price(userid, product_code, price, add_current_time, response_time, source) values(?,?,?,?,?,?) ";
			int i = jdbcTemplate.update(sql, userId,productCode,price,currentDate, responseTime, source);

			return i;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}
}
