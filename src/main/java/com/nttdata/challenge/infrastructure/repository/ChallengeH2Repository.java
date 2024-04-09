package com.nttdata.challenge.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nttdata.challenge.domain.entity.QueryResult;

public interface ChallengeH2Repository extends JpaRepository<QueryResult, Long> {
}
