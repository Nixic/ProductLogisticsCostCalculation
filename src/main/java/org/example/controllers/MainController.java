package org.example.controllers;

import org.example.dto.BillToPay;
import org.example.dto.ProductLot;
import org.example.service.CalculateCost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final CalculateCost calculateCostService;
    private final CalculateCost multithreadingService;
    private final CalculateCost callableService;

    public MainController(@Qualifier("calculateCostServiceImpl") CalculateCost calculateCostService,
                          @Qualifier("calculateCostMultithreadingServiceImpl") CalculateCost multithreadingService,
                          @Qualifier("calculateCostCallableServiceImpl") CalculateCost callableService) {
        this.calculateCostService = calculateCostService;
        this.multithreadingService = multithreadingService;
        this.callableService = callableService;
    }

    @PostMapping()
    public List<ProductLot> singleThreadingCalc(@RequestBody BillToPay billToPay) {
        return calculateCostService.calculateCostService(billToPay.getProductLots(), billToPay.getCommonTransportationCost());
    }

    @PostMapping("/multithreading")
    public String multithreadingCalc(@RequestBody BillToPay billToPay) {
        multithreadingService.calculateCostService(billToPay.getProductLots(), billToPay.getCommonTransportationCost());
        return "See result in console. Call /callable endpoint, To see result of multithreading calculation here";
    }

    @PostMapping("/callable")
    public List<ProductLot> callableCalc(@RequestBody BillToPay billToPay) {
        return callableService.calculateCostService(billToPay.getProductLots(), billToPay.getCommonTransportationCost());
    }

}
