package org.example.dto;

import lombok.Data;

/**
 * One line of the product on the invoice.
 */
@Data
public class ProductLot {

    // product
    private Product product;
    // count of product on the invoice
    private Double count;

    public ProductLot(Product product, Double count) {
        this.product = product;
        this.count = count;
    }
}
