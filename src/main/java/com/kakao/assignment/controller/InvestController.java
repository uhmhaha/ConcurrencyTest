package com.kakao.assignment.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.assignment.dto.InvestAssetReqDto;
import com.kakao.assignment.service.InvestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class InvestController {

	private final InvestService investService;
	private final StringRedisTemplate redisTemplate;
	
	@GetMapping("/")
	public String Welcome() {
		return "OK";
	}
	
	@PostMapping("/save")
	public Long save(@RequestBody InvestAssetReqDto reqDto) {
		log.info(" >>>>>>>>>>>> [save] = {}", reqDto);
		return investService.invest(reqDto);
	}
}
