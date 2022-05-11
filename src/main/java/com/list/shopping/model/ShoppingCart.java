package com.list.shopping.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "products_cart")
    private List<Product> products;

    @OneToOne(mappedBy = "cart")
    private User user;

    @SuppressWarnings("unused")
    private ShoppingCart() {}

    public ShoppingCart(User user) {
        this.products = new ArrayList<>();
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProduct(Product product) {
        this.products.add(product);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

}
