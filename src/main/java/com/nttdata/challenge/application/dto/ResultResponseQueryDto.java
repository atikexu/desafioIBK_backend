package com.nttdata.challenge.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nttdata.challenge.domain.entity.QueryResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultResponseQueryDto {
	private Integer size;
	private Integer pages;
    private List<QueryResult> queryList;

}
