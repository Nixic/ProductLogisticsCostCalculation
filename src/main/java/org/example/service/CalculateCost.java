package org.example.service;

import org.example.dto.ProductLot;

import java.math.BigDecimal;
import java.util.List;

public interface CalculateCost {

    List<ProductLot> calculateCostService(List<ProductLot> productLots, BigDecimal planingCost);

    List<ProductLot> calculateAverageCost(List<ProductLot> productLotList, BigDecimal planingCost,
                                          BigDecimal commonProductVolume, BigDecimal commonProductWeight);

}
