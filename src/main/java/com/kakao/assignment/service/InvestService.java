package com.kakao.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.assignment.dto.InvestAssetReqDto;
import com.kakao.assignment.repository.RedisCrudRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestService {
	
	private final RedisCrudRepository redisCrudRepository;
	
	
	@Transactional
	public Long invest(InvestAssetReqDto req) {
		
		return redisCrudRepository.save(req.toRedisHash()).getId(); 
	}
	

}
