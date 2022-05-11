package com.list.shopping.service.consumer;

import com.list.shopping.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerServiceImpl implements ConsumerService{
    @Value("${url.taco}")
    private String url;

    public Product[] consumerApi(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Product[].class);
    }

}
