package org.example.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillToPayParams {

    BigDecimal commonVolume;
    BigDecimal commonWeight;

}
