package com.list.shopping.service.product;

import com.list.shopping.model.Product;

import java.util.List;

public interface ProductService {
    void save (List<Product> productList);

    void saveByApiReturn();

    List<Product> findAll();

    void deleteProduct(Product product);

    void deleteAllProducts(List<Product> products);

}
