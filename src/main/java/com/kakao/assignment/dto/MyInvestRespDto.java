package com.kakao.assignment.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.kakao.assignment.vo.Invest;
import com.kakao.assignment.vo.InvestAsset;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyInvestRespDto {

	//상품 ID, 상품 제목, 총 모집금액, 나의 투자금액, 투자일시 
	@Id
	private Long assetId;
	private String assetTitle;
	private Long assetTotalAmount;
	private Long investAmount;
	private LocalDateTime investDate;

	public MyInvestRespDto(InvestAsset investAsset, Invest invest) {
		this.assetId = investAsset.getId();
		this.assetTitle = investAsset.getAssetTitle();
		this.assetTotalAmount= investAsset.getAssetCurrentAmount();
		this.investAmount= invest.getInvestAmount();
		this.investDate= invest.getInvestDate();
	}
	
}
