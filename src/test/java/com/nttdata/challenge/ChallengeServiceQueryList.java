package com.nttdata.challenge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.challenge.application.dto.ResultResponseQueryDto;
import com.nttdata.challenge.domain.entity.QueryResult;
import com.nttdata.challenge.domain.service.impl.ChallengeServiceImpl;
import com.nttdata.challenge.infrastructure.repository.ChallengeH2Repository;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceQueryList {

	@InjectMocks
    private ChallengeServiceImpl challengeServiceImpl;
	
	@Mock
    private ChallengeH2Repository challengeH2Repository;
	
	@Test
	void exchangeRate() {
		Integer size = 5;
		Integer page = 1;
		LocalDateTime date1 = LocalDateTime.of(2024, 4, 8, 12, 0);
		LocalDateTime date2 = LocalDateTime.of(2024, 4, 9, 9, 30);
		List<QueryResult> listQuery = new ArrayList<>();
		listQuery.add(new QueryResult(1L, date1, 100.0, 110.0, "USD", "EUR", 1.1));
		listQuery.add(new QueryResult(2L, date2, 200.0, 220.0, "EUR", "USD", 1.1));
		ResultResponseQueryDto resultResponseQueryDto = ResultResponseQueryDto.builder()
				.size(2)
				.pages(1)
				.queryList(listQuery)
				.build();
		when(challengeH2Repository.findAll()).thenReturn(listQuery);
		
		ResultResponseQueryDto result = challengeServiceImpl.queryList(size, page);
		
		assertEquals(result.getSize(), resultResponseQueryDto.getSize());
		assertEquals(result.getPages(), resultResponseQueryDto.getPages());
		assertEquals(result.getQueryList().size(), 2);
	}

}
