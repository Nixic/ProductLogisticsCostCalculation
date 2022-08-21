package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

/**
 * One line of the product on the bill to pay.
 */
@Data
public class ProductLot {

    // product
    private Product product;
    // count of product on the bill to pay
    @NotNull(message = "Product lot count can't be null")
    private Double count;
    // calculated transportation cost of one line on bill to pay
    private BigDecimal transportationCost;

    public ProductLot(Product product, Double count) {
        this.product = product;
        this.count = count;
    }

    @Override
    public String toString() {
        return "ProductLot{" +
                "product=" + product +
                ", count=" + count +
                ", transportationCost=" + transportationCost +
                '}';
    }
}
