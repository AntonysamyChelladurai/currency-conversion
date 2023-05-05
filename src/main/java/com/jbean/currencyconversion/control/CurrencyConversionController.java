package com.jbean.currencyconversion.control;

import com.jbean.currencyconversion.dto.CurrencyConversion;
import com.jbean.currencyconversion.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyExchangeProxy proxy;

    @GetMapping("/hello")
    public String greetings(){
        return "Hello CurrencyConversion !!!";
    }
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
            ){

        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from",from);
        uriVariable.put("to",to);
        ResponseEntity<CurrencyConversion> currencyresponse=new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversion.class,uriVariable);
        CurrencyConversion currencyConversion= currencyresponse.getBody();
        if (currencyConversion != null) {
            currencyConversion.setQuantity(quantity);
            currencyConversion.setEnvironment(currencyConversion.getEnvironment()+"  Rest Template");
            currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversion_Multiple()));
        }
        return currencyConversion;
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFegin(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        CurrencyConversion currencyConversion= proxy.retrieveExchange(from,to);
        if (currencyConversion != null) {
            currencyConversion.setQuantity(quantity);
            currencyConversion.setEnvironment(currencyConversion.getEnvironment()+"  Feign");
            currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversion_Multiple()));
        }
        return currencyConversion;
    }
}
