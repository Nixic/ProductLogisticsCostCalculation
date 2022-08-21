package org.example.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Bill to pay with lines of product and common sum of planning transportation cost
 */
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillToPay {

    @Valid
    @NotEmpty(message = "Input products list cannot be empty.")
    List<ProductLot> productLots;

    @NotNull(message = "Common transport cost can't be null")
    @Digits(message = "Common transport cost must be a number", integer = 10, fraction = 3)
    BigDecimal commonTransportationCost;

}
