package org.example.service;

import org.example.dto.Product;
import org.example.dto.ProductLot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Callable;

public class CallableCalculate implements Callable<BigDecimal> {

    private final BigDecimal commonProductVolume;
    private final BigDecimal commonProductWeight;
    private final ProductLot productLot;
    private final BigDecimal planingCost;

    CallableCalculate(BigDecimal commonProductVolume, BigDecimal commonProductWeight, ProductLot productLot, BigDecimal planingCost) {
        this.commonProductVolume = commonProductVolume;
        this.commonProductWeight = commonProductWeight;
        this.productLot = productLot;
        this.planingCost = planingCost;
    }

    @Override
    public BigDecimal call() {
        Product product = productLot.getProduct();
        BigDecimal lotCount = BigDecimal.valueOf(productLot.getCount());

        BigDecimal percentOfLotVolume = product.getVolume().multiply(lotCount).multiply(BigDecimal.valueOf(100.00)
                .divide(commonProductVolume, 24, RoundingMode.HALF_UP));
        BigDecimal percentOfLotWeight = product.getWeight().multiply(lotCount).multiply(BigDecimal.valueOf(100.00)
                .divide(commonProductWeight, 24, RoundingMode.HALF_UP));

        BigDecimal costByVolume = planingCost.divide(BigDecimal.valueOf(100.00), 6, RoundingMode.HALF_UP)
                .multiply(percentOfLotVolume);
        BigDecimal costByWeight = planingCost.divide(BigDecimal.valueOf(100.00), 6, RoundingMode.HALF_UP)
                .multiply(percentOfLotWeight);

        BigDecimal averagePlanLotCost = (costByVolume.add(costByWeight)).divide(BigDecimal.valueOf(2.0), 6, RoundingMode.HALF_UP);
//        System.out.printf("%nAverage plan cost for product %s is %s", product.getName(), averagePlanLotCost);
        System.out.flush();
        return averagePlanLotCost;
    }
}
