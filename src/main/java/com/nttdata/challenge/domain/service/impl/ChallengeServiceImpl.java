package com.nttdata.challenge.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.nttdata.challenge.application.dto.ExchangeRateResponseDto;
import com.nttdata.challenge.application.dto.ResultRequestDto;
import com.nttdata.challenge.application.dto.ResultResponseDto;
import com.nttdata.challenge.application.dto.ResultResponseQueryDto;
import com.nttdata.challenge.domain.entity.QueryResult;
import com.nttdata.challenge.domain.service.ChallengeService;
import com.nttdata.challenge.infrastructure.feign.ExchangeRateFeign;
import com.nttdata.challenge.infrastructure.repository.ChallengeH2Repository;

import feign.FeignException;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class ChallengeServiceImpl implements ChallengeService{
	
	@Autowired
    private ExchangeRateFeign exchangeRateFeign;
	
	@Autowired
    private ChallengeH2Repository challengeH2Repository;

    @Override
    public ResultResponseDto exchangeRate(ResultRequestDto resultRequestDto) {
    	log.info("log de exchangeRate");
    	ResultResponseDto response = new ResultResponseDto();
    	try {
    		ExchangeRateResponseDto exchangeRateResponseDto =  exchangeRateFeign.getLatestExchangeRates(resultRequestDto.getOriginCurrency());
    		Double amount = resultRequestDto.getAmount();
    		String originCurrency = resultRequestDto.getOriginCurrency();
    		String destinationCurrency = resultRequestDto.getDestinationCurrency();
    		if(exchangeRateResponseDto.getResult().equals("success")) {
    			Map<String, Double> conversionRates = exchangeRateResponseDto.getConversionRates();
        		Double exchangeRate = conversionRates.get(destinationCurrency);
        		Double amountExchangeRate = amount*exchangeRate;
        		response = ResultResponseDto.builder()
        				.amount(amount)
        				.amountExchangeRate(amountExchangeRate)
        				.originCurrency(originCurrency)
        				.destinationCurrency(destinationCurrency)
        				.exchangeRate(exchangeRate)
        				.build();
        		QueryResult queryResult = QueryResult.builder()
        				.date(LocalDateTime.now())
        				.amount(amount)
        				.amountExchangeRate(amountExchangeRate)
        				.originCurrency(originCurrency)
        				.destinationCurrency(destinationCurrency)
        				.exchangeRate(exchangeRate)
        				.build();
                challengeH2Repository.save(queryResult);
        		return response;
    		} 
    	} catch (FeignException  e) {
    		log.error("Error de Feign " + e.getMessage());
    	} catch (HttpServerErrorException e) {
    		log.error("Error de servidor remoto: " + e.getMessage());
        } catch (Exception e) {
        	log.error("Error desconocido: " + e.getMessage());
        }
        return response;
    }

	@Override
	public ResultResponseQueryDto queryList(Integer size, Integer page) {
		List<QueryResult> listQuery =  challengeH2Repository.findAll();
		Integer size1 = listQuery.size();
		Integer pages = (int) Math.ceil((double) size1 / size);
		listQuery = listQuery.stream()
				.skip((page - 1) * size)
				.limit(size)
				.toList();
		return ResultResponseQueryDto.builder()
				.size(size1)
				.pages(pages)
				.queryList(listQuery)
				.build();
	} 
}
