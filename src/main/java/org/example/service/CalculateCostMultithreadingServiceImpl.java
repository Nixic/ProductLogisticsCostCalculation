package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BillToPayParams;
import org.example.dto.ProductLot;
import org.example.service.utils.ProductLotUtil;
import org.example.utils.ArrayUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateCostMultithreadingServiceImpl implements CalculateCost {

    private int batchThreadSize = 2;

    public void setBatchThreadSize(int batchThreadSize) {
        this.batchThreadSize = batchThreadSize;
    }

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

        CopyOnWriteArrayList<ProductLot> onWriteArrayList = new CopyOnWriteArrayList<>();
        List<List<ProductLot>> listOfProductLotSubList = ArrayUtil.divideListToSubLists(productLotList, batchThreadSize);

        for (int i = 0; i < listOfProductLotSubList.size(); i++) {
            List<ProductLot> productLots = listOfProductLotSubList.get(i);
            MultiThreadCalculate multiThreadCalculate = new MultiThreadCalculate(productLots, planingCost, commonProductVolume, commonProductWeight);
            Thread productThread = new Thread(multiThreadCalculate, "ProductThread_" + i);
            productThread.start();
            onWriteArrayList.addAll(multiThreadCalculate.getBatch()); // here data not updated, need use sleep to see result in array
        }
        return onWriteArrayList;

    }

}
