package com.list.shopping.service.user;

import com.list.shopping.model.ShoppingCart;
import com.list.shopping.model.User;
import com.list.shopping.repository.UserRepository;
import com.list.shopping.service.shoppingCart.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ShoppingCartServiceImpl cartService;

    public User save(User user){
        repository.save(user);
        ShoppingCart cart = cartService.save(user);
        user.setCart(cart);
        repository.save(user);
        return user;
    }
}
