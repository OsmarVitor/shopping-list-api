package com.list.shopping.service.shoppingCart;

import com.list.shopping.model.Product;
import com.list.shopping.model.ShoppingCart;
import com.list.shopping.model.User;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart save(User user);

    void addToCart(String username, List<Product> productList);

    void addToCartByName(String username, List<String> productList);

    void removeToCart(String username, List<Product> productList);

    void clearCart(String username);

    List<Product> findAllProductsOfUser(String username);
}
