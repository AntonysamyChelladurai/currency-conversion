package com.jbean.currencyconversion.control;

import com.jbean.currencyconversion.dto.CurrencyConversion;
import com.jbean.currencyconversion.proxy.CurrencyExchangeProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyExchangeProxy proxy;

    @Autowired
    RestTemplate restTemplate;

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
        log.info(" CurrencyConversion !!! calculateCurrencyConversion Method Begin");
        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from",from);
        uriVariable.put("to",to);
       /* ResponseEntity<CurrencyConversion> currencyresponse=new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversion.class,uriVariable);*/

        ResponseEntity<CurrencyConversion> currencyresponse=new RestTemplate().getForEntity("http://currency-exc-service:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversion.class,uriVariable);
        CurrencyConversion currencyConversion= currencyresponse.getBody();
        if (currencyConversion != null) {
            currencyConversion.setQuantity(quantity);
            currencyConversion.setEnvironment(currencyConversion.getEnvironment()+"  Rest Template");
            currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversion_Multiple()));
        }
        log.info(" CurrencyConversion !!! calculateCurrencyConversion Method Begin");
        return currencyConversion;
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFegin(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        log.info(" CurrencyConversion !!! calculateCurrencyConversionFegin Method Begin");
        CurrencyConversion currencyConversion= proxy.retrieveExchange(from,to);
        log.info(" calculateCurrencyConversionFegin Method currencyConversion Object :: "+currencyConversion.toString());
        if (currencyConversion != null) {
            currencyConversion.setQuantity(quantity);
            currencyConversion.setEnvironment(currencyConversion.getEnvironment()+"  Feign");
            currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversion_Multiple()));
        }
        log.info(" CurrencyConversion !!! calculateCurrencyConversionFegin Method End");
        return currencyConversion;
    }
}
