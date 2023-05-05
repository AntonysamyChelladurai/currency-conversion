package com.jbean.currencyconversion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversion {
    //@Id
    private long id;
   // @Column(name = "CURRENCY_FROM")
    private String from;
  //  @Column(name = "CURRENCY_TO")
    private String to;
    private BigDecimal quantity;
    private BigDecimal conversion_Multiple;
    private BigDecimal totalCalculatedAmount;
    private String environment;
}

