package com.nttdata.challenge.application.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.challenge.application.dto.ResultRequestDto;
import com.nttdata.challenge.application.dto.ResultResponseDto;
import com.nttdata.challenge.application.dto.ResultResponseQueryDto;
import com.nttdata.challenge.domain.service.ChallengeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ChallengeController {
	
    private ChallengeService challengeService;
    
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping("/exchangerate")
    public ResultResponseDto exchangeRate(@RequestBody ResultRequestDto resultRequestDto){
        return challengeService.exchangeRate(resultRequestDto);
    }
    
    @GetMapping("/querylist")
    public ResultResponseQueryDto queryList(HttpServletRequest request){
    	Integer page = Integer.parseInt(request.getHeader("page"));
  	    Integer size = Integer.parseInt(request.getHeader("size"));
        return challengeService.queryList(size, page);
    }

}
