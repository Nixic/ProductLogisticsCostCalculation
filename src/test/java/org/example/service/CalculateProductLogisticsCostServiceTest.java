package org.example.service;

import org.example.dto.Product;
import org.example.dto.ProductLot;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class CalculateProductLogisticsCostServiceTest {

    List<ProductLot> productLotList = new ArrayList<>();

    @Test
    public void calculateAverageCost() {
        // new product 50 cubic meters and 2000 kilograms
        Product product_1 = new Product("Product_1", BigDecimal.valueOf(50.0), BigDecimal.valueOf(2000.0));
        Product product_2 = new Product("Product_2", BigDecimal.valueOf(100.0), BigDecimal.valueOf(1000.0));
        Product product_3 = new Product("Product_3", BigDecimal.valueOf(200.0), BigDecimal.valueOf(1000.0));

        // add products to list (as lot, item of invoice, etc...)
        productLotList.add(new ProductLot(product_1, 3000.0));
        productLotList.add(new ProductLot(product_2, 2000.0));
        productLotList.add(new ProductLot(product_3, 200.0));

        new CalculateProductLogisticsCostService(productLotList, BigDecimal.valueOf(100000.0));

    }

}