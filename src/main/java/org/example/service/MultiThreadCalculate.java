package org.example.service;

import org.example.dto.Product;
import org.example.dto.ProductLot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class MultiThreadCalculate implements Runnable {

    private List<ProductLot> batch;
    private final BigDecimal planingCost;
    private final BigDecimal commonProductVolume;
    private final BigDecimal commonProductWeight;

    public MultiThreadCalculate(List<ProductLot> batch, BigDecimal planingCost, BigDecimal commonProductVolume, BigDecimal commonProductWeight) {
        this.batch = batch;
        this.planingCost = planingCost;
        this.commonProductVolume = commonProductVolume;
        this.commonProductWeight = commonProductWeight;
    }

    public List<ProductLot> getBatch() {
        return batch;
    }

    @Override
    public void run() {
        calcAveragePlanLotCost(batch, planingCost, commonProductVolume, commonProductWeight);
    }

    private void calcAveragePlanLotCost(List<ProductLot> batch, BigDecimal planingCost, BigDecimal
            commonProductVolume, BigDecimal commonProductWeight) {
        for (ProductLot productLot : batch) {
            Product product = productLot.getProduct();
            BigDecimal lotCount = BigDecimal.valueOf(productLot.getCount());
            BigDecimal percentOfLotWeight = product.getWeight().multiply(lotCount).multiply(BigDecimal.valueOf(100.00)
                    .divide(commonProductWeight, 24, RoundingMode.HALF_UP));
            BigDecimal percentOfLotVolume = product.getVolume().multiply(lotCount).multiply(BigDecimal.valueOf(100.00)
                    .divide(commonProductVolume, 24, RoundingMode.HALF_UP));

            BigDecimal costByWeight = planingCost.divide(BigDecimal.valueOf(100.00), 6, RoundingMode.HALF_UP)
                    .multiply(percentOfLotWeight);
            BigDecimal costByVolume = planingCost.divide(BigDecimal.valueOf(100.00), 6, RoundingMode.HALF_UP)
                    .multiply(percentOfLotVolume);

            BigDecimal averagePlanLotCost = (costByWeight.add(costByVolume)).divide(BigDecimal.valueOf(2.0), 6, RoundingMode.HALF_UP);
            System.out.printf("Average plan cost for product %s is %s ThreadName: %s %n",
                    productLot.getProduct().getName(), averagePlanLotCost, Thread.currentThread().getName());
            System.out.flush();
        }


    }

}
