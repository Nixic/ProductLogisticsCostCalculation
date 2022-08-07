package org.example.service.utils;

import lombok.experimental.UtilityClass;
import org.example.dto.BillToPayParams;
import org.example.dto.ProductLot;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class ProductLotUtil {

    /**
     *  Calculate common volume and common weight of all items on the bill to pay.
     * @param productLots items of product on the bill to pay
     * @return  Bill to pay parameters
     */
    public BillToPayParams calculateCommonVolumeAndWeight(List<ProductLot> productLots) {
        BigDecimal commonProductVolume = BigDecimal.valueOf(0.00);
        BigDecimal commonProductWeight = BigDecimal.valueOf(0.00);
        for (ProductLot productLot : productLots) {
            BigDecimal lotVolume = productLot.getProduct().getVolume().multiply(BigDecimal.valueOf(productLot.getCount()));
            BigDecimal lotWeight = productLot.getProduct().getWeight().multiply(BigDecimal.valueOf(productLot.getCount()));
            commonProductVolume = commonProductVolume.add(lotVolume);
            commonProductWeight = commonProductWeight.add(lotWeight);
        }
        System.out.printf("Common volume %s %n", commonProductVolume);
        System.out.printf("Common weight %s %n%n", commonProductWeight);
        return new BillToPayParams(commonProductVolume, commonProductWeight);
    }


    }
