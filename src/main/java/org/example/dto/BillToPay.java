package org.example.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

/**
 * Bill to pay with lines of product and common sum of planning transportation cost
 */
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillToPay {

    List<ProductLot> productLots;
    BigDecimal commonTransportationCost;

}
