package com.list.shopping.service.product;

import com.list.shopping.model.Product;
import com.list.shopping.repository.ProductRepository;
import com.list.shopping.service.consumer.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ConsumerService consumerService;

    @Override
    public void save(List<Product> productList) {
        repository.saveAll(productList);
    }

    @Override
    public void saveByApiReturn() {
        List<Product> productList = Arrays.asList(consumerService.consumerApi());
        save(productList);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteProduct(Product product) {
        repository.delete(product);
    }

    @Override
    public void deleteAllProducts(List<Product> products) {
        repository.deleteAll(products);
    }


}
