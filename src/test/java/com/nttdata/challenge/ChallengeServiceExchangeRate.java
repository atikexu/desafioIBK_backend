package com.nttdata.challenge;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpServerErrorException;

import com.nttdata.challenge.application.dto.ExchangeRateResponseDto;
import com.nttdata.challenge.application.dto.ResultRequestDto;
import com.nttdata.challenge.application.dto.ResultResponseDto;
import com.nttdata.challenge.domain.entity.QueryResult;
import com.nttdata.challenge.domain.service.impl.ChallengeServiceImpl;
import com.nttdata.challenge.infrastructure.feign.ExchangeRateFeign;
import com.nttdata.challenge.infrastructure.repository.ChallengeH2Repository;

import feign.FeignException;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceExchangeRate {
	
	@InjectMocks
    private ChallengeServiceImpl challengeServiceImpl;
	
	@Mock
    private ExchangeRateFeign exchangeRateFeign;
	
	@Mock
    private ChallengeH2Repository challengeH2Repository;

	@Test
	void exchangeRateOK() {
		ResultRequestDto resultRequestDto = ResultRequestDto.builder()
				.amount(10.0)
				.originCurrency("USD")
				.destinationCurrency("PEN")
				.build();
		Map<String, Double> conversionRates = Map.of(
			    "PEN", 3.6787,
			    "EUR", 0.9217
			);
		ExchangeRateResponseDto exchangeRateResponseDto = ExchangeRateResponseDto.builder()
				.result("success")
				.conversionRates(conversionRates)
				.build();
		ResultResponseDto resultResponseDto = ResultResponseDto.builder()
				.amount(10.0)
				.amountExchangeRate(36.787)
				.originCurrency("USD")
				.destinationCurrency("PEN")
				.exchangeRate(3.6787)
				.build();
		QueryResult queryResult = QueryResult.builder()
				.date(LocalDateTime.now())
				.amount(10.0)
				.amountExchangeRate(36.787)
				.originCurrency("USD")
				.destinationCurrency("PEN")
				.exchangeRate(3.6787)
				.build();
		when(exchangeRateFeign.getLatestExchangeRates(anyString())).thenReturn(exchangeRateResponseDto);
		when(challengeH2Repository.save(queryResult)).thenReturn(queryResult);
		
		ResultResponseDto result = challengeServiceImpl.exchangeRate(resultRequestDto);
		
		assertEquals(result.getAmount(), resultResponseDto.getAmount());
		assertEquals(result.getAmountExchangeRate(), resultResponseDto.getAmountExchangeRate());
		assertEquals(result.getOriginCurrency(), resultResponseDto.getOriginCurrency());
		assertEquals(result.getDestinationCurrency(), resultResponseDto.getDestinationCurrency());
		assertEquals(result.getExchangeRate(), resultResponseDto.getExchangeRate());
	}
	
	@Test
	void exchangeRateErrorFeign() {
		ResultRequestDto resultRequestDto = ResultRequestDto.builder()
				.amount(10.0)
				.originCurrency("USD")
				.destinationCurrency("PEN")
				.build();
		when(exchangeRateFeign.getLatestExchangeRates(anyString())).thenThrow(FeignException.class);
		
		ResultResponseDto result = challengeServiceImpl.exchangeRate(resultRequestDto);
		
		assertEquals(result.getAmount(), null);
	}
	
	@Test
	void exchangeRateErrorHttp() {
		ResultRequestDto resultRequestDto = ResultRequestDto.builder()
				.amount(10.0)
				.originCurrency("USD")
				.destinationCurrency("PEN")
				.build();
		when(exchangeRateFeign.getLatestExchangeRates(anyString())).thenThrow(HttpServerErrorException.class);
		
		ResultResponseDto result = challengeServiceImpl.exchangeRate(resultRequestDto);
		
		assertEquals(result.getAmount(), null);
	}

}
