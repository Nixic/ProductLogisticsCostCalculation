package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductLot;
import org.example.service.utils.ProductLotUtil;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class MultiThreadCalculate implements Runnable {

    private final List<ProductLot> batch;
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
            BigDecimal averagePlanLotCost = ProductLotUtil.calculateAverageCostOfProductLot(planingCost, commonProductVolume, commonProductWeight, productLot);
            String costInfo = String.format("Average plan cost for product %s is %s ThreadName: %s",
                    productLot.getProduct().getName(), averagePlanLotCost, Thread.currentThread().getName());
            log.info(costInfo);
        }


    }

}
