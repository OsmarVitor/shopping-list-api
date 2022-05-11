package com.list.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "products")
@JsonIgnoreProperties
public class Product {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @JsonProperty(value = "description", required = true)
    private String name;

    private String status;

    public Product(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    private Product() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product(UUID uuid, String name, String status) {
        this.uuid = uuid;
        this.name = name;
        this.status = status;
    }
}
