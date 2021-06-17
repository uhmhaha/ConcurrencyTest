package com.kakao.assignment.vo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@RedisHash("myInvestInfo")
public class MyInvestInfo {

	//상품 ID, 상품 제목, 총 모집금액, 나의 투자금액, 투자일시 
	@Id
	private Long id;
	private String assetTitle;
	private Long assetTotalAmount;
	private Long myInvestAmount;
	private LocalDateTime investDate;
	
	@Builder
	public MyInvestInfo(Long id, String assetTitle, Long assetTotalAmount, Long myInvestAmount,
			LocalDateTime investDate) {
		this.id = id;
		this.assetTitle = assetTitle;
		this.assetTotalAmount = assetTotalAmount;
		this.myInvestAmount = myInvestAmount;
		this.investDate = investDate;

	}
	
}
