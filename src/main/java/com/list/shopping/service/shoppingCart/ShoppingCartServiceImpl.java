package com.list.shopping.service.shoppingCart;

import com.list.shopping.exception.ProductNotFoundException;
import com.list.shopping.exception.UserNotFoundException;
import com.list.shopping.model.Product;
import com.list.shopping.model.ShoppingCart;
import com.list.shopping.model.User;
import com.list.shopping.repository.ProductRepository;
import com.list.shopping.repository.ShoppingChartRepository;
import com.list.shopping.repository.UserRepository;
import com.list.shopping.service.product.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingChartRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ShoppingCart save(User user) {
        ShoppingCart cart = new ShoppingCart(user);
        return repository.save(cart);
    }

    @Override
    public void addToCart(String username, List<Product> productList) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        productService.save(productList);

        for(Product product : productList){
            product.setStatus("available");
            user.getCart().setProduct(product);
            repository.save(user.getCart());
        }

    }

    @Override
    public void addToCartByName(String username, List<String> productListNames) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        for(String productName : productListNames){
            Product product = productRepository.findByName(productName).orElseThrow(() -> new ProductNotFoundException(productName));
            product.setStatus("available");
            user.getCart().setProduct(product);
            repository.save(user.getCart());

        }

    }

    @Override
    public List<Product> findAllProductsOfUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return user.getCart().getProducts();
    }

    @Override
    public void removeToCart(String username, List<Product> productList) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        for(Product product : productList) {
            Product productToDelete = productRepository.findByName(product.getName()).orElseThrow(() -> new ProductNotFoundException(product.getName()));
            productToDelete.setStatus("deleted");
            user.getCart().setProduct(productToDelete);
            productRepository.save(productToDelete);
            repository.save(user.getCart());
        }

    }

    @Override
    public void clearCart(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<Product> productList = user.getCart().getProducts();
        productService.deleteAllProducts(productList);
    }

}
