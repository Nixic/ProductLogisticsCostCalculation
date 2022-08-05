package org.example.service;

import org.example.dto.ProductLot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CalculateCostCallableService {

    private BigDecimal commonProductVolume = BigDecimal.valueOf(0.00);
    private BigDecimal commonProductWeight = BigDecimal.valueOf(0.00);

    /**
     * Calculate and uniform distribution of common sum of money between product items in invoice in dependence on weight and volume percent.
     *
     * @param productLots list of products on the bill to pay
     * @param planingCost known planing cost on transportation of all items on the bill to pay
     */
    public CalculateCostCallableService(List<ProductLot> productLots, BigDecimal planingCost) {
        calculateCommonWeightAndVolume(productLots);
        calculateAverageCost(productLots, planingCost);
    }

    /**
     * Calculate common weight and volume of all items on the bill to pay.
     *
     * @param productLots items of product on the bill to pay
     */
    private void calculateCommonWeightAndVolume(List<ProductLot> productLots) {
        for (ProductLot productLot : productLots) {
            BigDecimal lotVolume = productLot.getProduct().getVolume().multiply(BigDecimal.valueOf(productLot.getCount()));
            BigDecimal lotWeight = productLot.getProduct().getWeight().multiply(BigDecimal.valueOf(productLot.getCount()));
            commonProductVolume = commonProductVolume.add(lotVolume);
            commonProductWeight = commonProductWeight.add(lotWeight);
        }
        System.out.printf("%n");
        System.out.printf("Common volume %s %n%n", commonProductVolume);
        System.out.printf("Common weight %s %n", commonProductWeight);
    }


    private void calculateAverageCost(List<ProductLot> productLots, BigDecimal planingCost) {
        System.out.printf("Planing common cost %s %n", planingCost);

        BigDecimal averagePlanCostSum = BigDecimal.valueOf(0.00);
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (ProductLot productLot : productLots) {
            CallableCalculate callable = new CallableCalculate(commonProductVolume, commonProductWeight, productLot, planingCost);
            FutureTask<BigDecimal> futureTask = new FutureTask<>(callable);
            executorService.execute(futureTask);
            try {
                averagePlanCostSum = averagePlanCostSum.add(futureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("%n%nSum of calculated average costs (for visual control) is %s %n%n", averagePlanCostSum.setScale(3, RoundingMode.DOWN));
    }
}
