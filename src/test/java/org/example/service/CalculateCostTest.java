package org.example.service;

import org.example.dto.Product;
import org.example.dto.ProductLot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CalculateCostTest {

    static List<ProductLot> productLotList = new ArrayList<>();
    static BigDecimal planingCost = BigDecimal.valueOf(1000000.0);
    static String timeToCalculateStringFormat = "%n*** Time to calculate: %s ms. *** %n";
    @Test
    void contextLoads() {
    }

    @BeforeAll
    static void fillProductList() {
        for (int i = 1; i <= 1000; i++) {
            // new product 5+i cubic meters and 2000 kilograms
            Product newProduct = new Product("Product_" + i, BigDecimal.valueOf(5.0 + i), BigDecimal.valueOf(100.0 + i));
            productLotList.add(new ProductLot(newProduct, 100.0 + i));
        }
    }

    @Test
    public void calculateAverageCostSingleThread() {
        long startTime_0 = System.nanoTime();
        CalculateCostServiceImpl calculateCost = new CalculateCostServiceImpl();
        calculateCost.calculateCostService(productLotList, planingCost);
        System.out.printf(timeToCalculateStringFormat, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime_0));

    }


    @Test
    public void calculateAverageCostMultithreading() {
        int batchThreadSize = 20;

        long startTime_1 = System.nanoTime();
        CalculateCostMultithreadingServiceImpl calculateCostMultithreading = new CalculateCostMultithreadingServiceImpl();
        calculateCostMultithreading.setBatchThreadSize(batchThreadSize);
        calculateCostMultithreading.calculateCostService(productLotList, planingCost);
        try {
            int sleepTime = 200;
            Thread.sleep(sleepTime); // This need to see Time at the end of console (will added to execute time)
            System.out.printf(timeToCalculateStringFormat,
                    TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime_1 - sleepTime)); // ...  sleepTime subtracted
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void calculateAverageCostCallable() {
        long startTime_2 = System.nanoTime();
        CalculateCost calculateCostCallable = new CalculateCostCallableServiceImpl();
        calculateCostCallable.calculateCostService(productLotList, planingCost);
        System.out.printf(timeToCalculateStringFormat, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime_2));
    }

}