package org.example.service;

import org.example.dto.Product;
import org.example.dto.ProductLot;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CalculateCostCallableServiceTest {

    List<ProductLot> productLotList = new ArrayList<>();

    @Test
    void contextLoads() {
    }

    @Test
    public void calculateAverageCost() {
        for (int i = 1; i <= 100000; i++) {
            // new product 50+i cubic meters and 2000 kilograms
            Product newProduct = new Product("Product_" + i, BigDecimal.valueOf(5.0 + i), BigDecimal.valueOf(100.0 + i));
            productLotList.add(new ProductLot(newProduct, 100.0 + i));
        }
        long startTime = System.nanoTime();
        new CalculateCostCallableService(productLotList, BigDecimal.valueOf(100000.0));

        System.out.printf("*** Time to calculate: %s ms. *** %n", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

    }

}