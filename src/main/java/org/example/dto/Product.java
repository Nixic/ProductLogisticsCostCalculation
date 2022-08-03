package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Some product with known volume and weight from manufacturer.
 */
@Data
public class Product {

    private String name;
    private BigDecimal volume;
    private BigDecimal weight;

    public Product(String name, BigDecimal volume, BigDecimal weight) {
        this.name = name;
        this.volume = volume;
        this.weight = weight;
    }
}
