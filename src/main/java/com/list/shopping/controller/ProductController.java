package com.list.shopping.controller;

import com.list.shopping.model.Product;
import com.list.shopping.model.User;
import com.list.shopping.service.product.ProductService;
import com.list.shopping.service.shoppingCart.ShoppingCartService;
import com.list.shopping.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
public class ProductController {

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ProductService productService;

    @GetMapping("products/all")
    public List<Product> listProducts(){
        productService.saveByApiReturn();
        return productService.findAll();

    }

    @PostMapping("create-user")
    public ResponseEntity<User> createUser(@RequestBody User userToSave){
        User user = userService.save(userToSave);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/shopping-list/{username}/list-products")
                        .buildAndExpand(user.getUsername()).toUri())
                .build();
    }

    @PutMapping("/{username}/add/product")
    public ResponseEntity<Product> addProductToChartOfUser(@PathVariable String username, @RequestBody List<Product> productList){
        shoppingCartService.addToCart(username, productList);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/add/product-by-name")
    public ResponseEntity<Product> addProductToChartOfUserByName(@PathVariable String username,  @RequestBody List<String> productName){
        shoppingCartService.addToCartByName(username, productName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/list-products")
    public List<Product> listProductsOfUser(@PathVariable String username){
        return shoppingCartService.findAllProductsOfUser(username);
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<Product> removeProductToChartOfUser(@PathVariable String username, @RequestBody List<Product> productList){
        shoppingCartService.removeToCart(username, productList);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}/clear")
    public ResponseEntity<Product> clearChartOfUser(@PathVariable String username){
        shoppingCartService.clearCart(username);
        return ResponseEntity.noContent().build();
    }

}
