package com.list.shopping.repository;

import com.list.shopping.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShoppingChartRepository extends JpaRepository<ShoppingCart, UUID> {
}
