package org.example.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BillToPayParams;
import org.example.dto.ProductLot;
import org.example.service.utils.ProductLotUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class CalculateCostServiceImpl implements CalculateCost {

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
        log.info(String.format("Planing common cost %s", planingCost));

        BigDecimal averagePlanCostSum = BigDecimal.valueOf(0.00);
        for (ProductLot productLot : productLotList) {
            BigDecimal averagePlanLotCost = ProductLotUtil.calculateAverageCostOfProductLot(planingCost, commonProductVolume, commonProductWeight, productLot);
            productLot.setTransportationCost(averagePlanLotCost);
            String costInfo = String.format("Average plan cost for product %s is %s ThreadName: %s",
                    productLot.getProduct().getName(), averagePlanLotCost, Thread.currentThread().getName());
            log.info(costInfo);
            averagePlanCostSum = averagePlanCostSum.add(averagePlanLotCost);
        }
        String costSum = String.format("Sum of calculated average costs (for visual control) is %s", averagePlanCostSum.setScale(3, RoundingMode.HALF_UP));
        log.info(costSum);
        return productLotList;
    }




}
