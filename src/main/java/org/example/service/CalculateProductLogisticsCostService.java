package org.example.service;

import org.example.dto.Product;
import org.example.dto.ProductLot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculateProductLogisticsCostService {

    private BigDecimal commonProductVolume = BigDecimal.valueOf(0.00);
    private BigDecimal commonProductWeight = BigDecimal.valueOf(0.00);

    /**
     * Calculate and uniform distribution of common sum of money between product items in invoice in dependence on weight and volume percent.
     *
     * @param productLots list of products on the bill to pay
     * @param planingCost known planing cost on transportation of all items on the bill to pay
     */
    public CalculateProductLogisticsCostService(List<ProductLot> productLots, BigDecimal planingCost) {
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
            BigDecimal lotWeight = productLot.getProduct().getWeight().multiply(BigDecimal.valueOf(productLot.getCount()));
            BigDecimal lotVolume = productLot.getProduct().getVolume().multiply(BigDecimal.valueOf(productLot.getCount()));
            commonProductWeight = commonProductWeight.add(lotWeight);
            commonProductVolume = commonProductVolume.add(lotVolume);
        }
        System.out.printf("%n");
        System.out.printf("common weight %s %n", commonProductWeight);
        System.out.printf("common volume %s %n%n", commonProductVolume);
    }


    private void calculateAverageCost(List<ProductLot> productLots, BigDecimal planingCost) {
        System.out.printf("Planing common cost %s %n%n", planingCost);

        BigDecimal averagePlanCostSum = BigDecimal.valueOf(0.00);
        for (ProductLot productLot : productLots) {
            Product product = productLot.getProduct();
            BigDecimal lotCount = BigDecimal.valueOf(productLot.getCount());

            BigDecimal percentOfLotWeight = product.getWeight().multiply(lotCount).multiply(BigDecimal.valueOf(100.00)
                            .divide(commonProductWeight, 12, RoundingMode.HALF_UP));
            BigDecimal percentOfLotVolume = product.getVolume().multiply(lotCount).multiply(BigDecimal.valueOf(100.00)
                            .divide(commonProductVolume, 12, RoundingMode.HALF_UP));

            System.out.printf("Percent of weight %s %n", percentOfLotWeight);
            System.out.printf("Percent of value %s %n", percentOfLotVolume);

            BigDecimal costByWeight = planingCost.divide(BigDecimal.valueOf(100.00), 6, RoundingMode.HALF_UP)
                    .multiply(percentOfLotWeight);
            BigDecimal costByVolume = planingCost.divide(BigDecimal.valueOf(100.00), 6, RoundingMode.HALF_UP)
                    .multiply(percentOfLotVolume);

            System.out.printf("Cost by weight %s %n", costByWeight);
            System.out.printf("Cost by value %s %n", costByVolume);

            BigDecimal averagePlanLotCost = (costByWeight.add(costByVolume)).divide(BigDecimal.valueOf(2.0), 6, RoundingMode.HALF_UP);
            System.out.printf("Average plan cost for product %s is %s %n%n", product.getName(), averagePlanLotCost);

            averagePlanCostSum = averagePlanCostSum.add(averagePlanLotCost);
        }
        System.out.printf("Sum of calculated average costs (for visual control) is %s %n%n", averagePlanCostSum.setScale(3, RoundingMode.DOWN));
    }


}
