package com.jbean.currencyconversion.proxy;

import com.jbean.currencyconversion.dto.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(name = "currency-exchange", url="http://currency-exc-service:8000")
//@FeignClient(name = "currency-exc-service")
public interface CurrencyExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchange(
            @PathVariable String from,
            @PathVariable String to);
}
