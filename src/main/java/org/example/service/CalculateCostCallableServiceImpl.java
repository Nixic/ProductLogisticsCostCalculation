package org.example.service;

import org.example.dto.BillToPayParams;
import org.example.dto.ProductLot;
import org.example.service.utils.ProductLotUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
public class CalculateCostCallableServiceImpl implements CalculateCost {

    /**
     * Calculate and uniform distribution of common sum of money between product items in invoice in dependence on weight and volume percent.
     *
     * @param productLots list of products on the bill to pay
     * @param planingCost known planing cost on transportation of all items on the bill to pay
     */
    @Override
    public List<ProductLot> calculateCostService(List<ProductLot> productLots, BigDecimal planingCost) {
        BillToPayParams billToPayParams = ProductLotUtil.calculateCommonVolumeAndWeight(productLots);
        return calculateAverageCost(productLots, planingCost, billToPayParams.getCommonVolume(), billToPayParams.getCommonWeight());
    }

    @Override
    public List<ProductLot> calculateAverageCost(List<ProductLot> productLotList, BigDecimal planingCost,
                                                 BigDecimal commonProductVolume, BigDecimal commonProductWeight) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (ProductLot productLot : productLotList) {
            CallableCalculate callable = new CallableCalculate(commonProductVolume, commonProductWeight, productLot, planingCost);
            FutureTask<BigDecimal> futureTask = new FutureTask<>(callable);
            executorService.execute(futureTask);
            try {
                productLot.setTransportationCost(futureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return productLotList;
    }
}
