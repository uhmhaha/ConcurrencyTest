package com.kakao.assignment.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.kakao.assignment.vo.InvestAsset;

import lombok.Getter;

@Getter
public class InvestAssetRespDto {

	//상품 ID, 상품 제목, 총 모집금액, 현재 모집금액, 투자자 수, 투자모집상태(모집중, 모집 완료), 상품 모집기간
	@Id
	private Long id;
	private String assetTitle;
	private Long assetTotalAmount;
	private Long assetCurrentAmount;
	private Long investorNumbers;
	private String assetStatus;
	private Date assetValidFromDate;
	private Date assetValidToDate;
	
	public InvestAssetRespDto( InvestAsset investAsset ) {
		this.id = investAsset.getId();
		this.assetTitle = investAsset.getAssetTitle();
		this.assetTotalAmount = investAsset.getAssetTotalAmount();
		this.assetCurrentAmount = investAsset.getAssetCurrentAmount();
		this.investorNumbers = investAsset.getInvestorNumbers();
		this.assetStatus = investAsset.getAssetStatus();
		this.assetValidFromDate = investAsset.getAssetValidFromDate();
		this.assetValidToDate = investAsset.getAssetValidToDate();
		
	}
}
