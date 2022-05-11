package com.list.shopping.controller;

import com.list.shopping.model.Product;
import com.list.shopping.model.User;
import com.list.shopping.service.product.ProductService;
import com.list.shopping.service.shoppingCart.ShoppingCartService;
import com.list.shopping.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/sql/delete_all_tables.sql")
@TestPropertySource("classpath:application-test.properties")
public class ProductControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldGetAllProducts() throws Exception{

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1"));
        productList.add(new Product("Product 2"));
        productList.add(new Product("Product 3"));
        productService.save(productList);

        mockMvc
                .perform(get("/api/shopping-list/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").value("Product 1"))
                .andExpect(jsonPath("$.[1].description").value("Product 2"))
                .andExpect(jsonPath("$.[2].description").value("Product 3"));
    }

    @Test
    public void shouldCreateUser() throws Exception{
        String user = "{\n" +
                      "  \"username\": \"UserTest\"\n" +
                      "}";

        mockMvc
                .perform(post("/api/shopping-list/create-user")
                        .contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(status().isCreated());

        mockMvc
                .perform(get("/api/shopping-list/" + "UserTest" + "/list-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddProductToChartOfUser() throws Exception{
        User userTestAuthorized = new User("UserTestAuthorized");
        userService.save(userTestAuthorized);

        User userTestUnauthorized = new User("UserTestUnauthorized");
        userService.save(userTestUnauthorized);

        String productList = "" +
                "[\t{\"description\": \"Product 3\"},\n" +
                "\t{\"description\": \"Product 1\"},\n" +
                "\t{\"description\": \"Product 2\"}\n" +
                "]";

        mockMvc
                .perform(put("/api/shopping-list/" + userTestAuthorized.getUsername() + "/add/product")
                        .contentType(MediaType.APPLICATION_JSON).content(productList))
                .andExpect(status().isNoContent());

        mockMvc
                .perform(get("/api/shopping-list/" + userTestAuthorized.getUsername() + "/list-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").value("Product 3"))
                .andExpect(jsonPath("$.[1].description").value("Product 1"))
                .andExpect(jsonPath("$.[2].description").value("Product 2"));

        String responseOfUserEmpty = mockMvc
                .perform(get("/api/shopping-list/" + userTestUnauthorized.getUsername() + "/list-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Assert.assertEquals("Verifying that the user without products cannot search for products from other users", "[]", responseOfUserEmpty);
    }

}
