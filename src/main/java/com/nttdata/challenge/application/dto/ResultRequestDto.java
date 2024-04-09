package com.nttdata.challenge.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultRequestDto {
	private Double amount;
    private String originCurrency;
    private String destinationCurrency;
}
