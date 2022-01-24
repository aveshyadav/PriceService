package com.witzeal.priceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.witzeal.priceservice.jpa.entity.FastPriceEntity;

@Repository
public interface ProductRepository extends JpaRepository<FastPriceEntity, Long> {
}