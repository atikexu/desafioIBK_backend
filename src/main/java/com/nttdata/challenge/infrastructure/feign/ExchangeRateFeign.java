package com.nttdata.challenge.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nttdata.challenge.application.dto.ExchangeRateResponseDto;

@FeignClient(name = "ExchangeRateFeign", url = "${exchange.url}")
public interface ExchangeRateFeign {
	@GetMapping("/{originCurrency}")
    ExchangeRateResponseDto getLatestExchangeRates(@RequestParam("originCurrency") String originCurrency);
}