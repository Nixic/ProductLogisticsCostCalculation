package org.example.service.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BillToPayParams;
import org.example.dto.Product;
import org.example.dto.ProductLot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@UtilityClass
@Slf4j
public class ProductLotUtil {

    /**
     * Calculate common volume and common weight of all items on the bill to pay.
     *
     * @param productLots items of product on the bill to pay
     * @return Bill to pay parameters
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
        log.info(String.format("Common volume %s", commonProductVolume));
        log.info(String.format("Common weight %s %n", commonProductWeight));
        return new BillToPayParams(commonProductVolume, commonProductWeight);
    }

    /**
     * Calculate average cost for one line in bill to pay
     * @param planingCost common transportation cost of all items on bill to pay
     * @param commonProductVolume common volume of all items on the bill to pay.
     * @param commonProductWeight common weight of all items on the bill to pay.
     * @param productLot one line on bill to pay
     * @return average cost for one line in bill to pay
     */
    public BigDecimal calculateAverageCostOfProductLot(BigDecimal planingCost, BigDecimal commonProductVolume, BigDecimal commonProductWeight, ProductLot productLot) {
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

        return (costByWeight.add(costByVolume)).divide(BigDecimal.valueOf(2.0), 6, RoundingMode.HALF_UP);
    }


}
