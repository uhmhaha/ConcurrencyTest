package com.kakao.assignment.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.kakao.assignment.vo.InvestAsset;

import lombok.Getter;

@Getter
public class InvestAssetRespDto {

	//상품 ID, 상품 제목, 총 모집금액, 현재 모집금액, 투자자 수, 투자모집상태(모집중, 모집 완료), 상품 모집기간
	@Id
	private Long assetId;
	private String assetTitle;
	private Long assetTotalAmount;
	private Long assetCurrentAmount;
	private Long investorNumbers;
	private String assetStatus;
	private LocalDateTime assetValidFromDate;
	private LocalDateTime assetValidToDate;
	
	public InvestAssetRespDto( InvestAsset investAsset ) {
		this.assetId = investAsset.getId();
		this.assetTitle = investAsset.getAssetTitle();
		this.assetTotalAmount = investAsset.getAssetTotalAmount();
		this.assetCurrentAmount = investAsset.getAssetCurrentAmount();
		this.investorNumbers = investAsset.getInvestorNumbers();
		this.assetStatus = investAsset.getAssetStatus();
		this.assetValidFromDate = investAsset.getAssetValidFromDate();
		this.assetValidToDate = investAsset.getAssetValidToDate();
		
	}
}
