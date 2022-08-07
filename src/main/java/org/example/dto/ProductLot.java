package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * One line of the product on the bill to pay.
 */
@Data
public class ProductLot {

    // product
    private Product product;
    // count of product on the bill to pay
    private Double count;
    // calculated transportation cost of one line on bill to pay
    private BigDecimal transportationCost;

    public ProductLot(Product product, Double count) {
        this.product = product;
        this.count = count;
    }
}
