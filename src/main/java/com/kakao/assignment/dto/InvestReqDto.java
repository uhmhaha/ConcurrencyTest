package com.kakao.assignment.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.kakao.assignment.vo.Invest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class InvestReqDto {

	//상품 ID, 상품 제목, 총 모집금액, 나의 투자금액, 투자일시 
	@Id
//	private Long investId;
	private String userId;
	private Long assetId;
	private Long investAmount;
	private LocalDateTime investDate;
	
	@Builder
	public InvestReqDto(String userId, Long assetId, Long investAmount,
			LocalDateTime investDate) {
		
//		this.investId = investId;
		this.assetId = assetId;
		this.userId = userId;
		this.investAmount = investAmount;
		this.investDate = investDate;

	}	
	
	public Invest toRedisHash() {
		
		return Invest.builder()
//				.investId(investId)
				.assetId(assetId)
				.userId(userId)
				.investAmount(investAmount)
				.investDate(LocalDateTime.now())
				.build();
	}
}
