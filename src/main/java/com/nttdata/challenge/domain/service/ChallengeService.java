package com.nttdata.challenge.domain.service;

import com.nttdata.challenge.application.dto.ResultRequestDto;
import com.nttdata.challenge.application.dto.ResultResponseDto;
import com.nttdata.challenge.application.dto.ResultResponseQueryDto;

public interface ChallengeService {

    ResultResponseDto exchangeRate(ResultRequestDto resultRequestDto);
    ResultResponseQueryDto queryList(Integer size, Integer page);

}
