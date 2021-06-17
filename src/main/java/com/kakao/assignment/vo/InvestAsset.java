package com.kakao.assignment.vo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@RedisHash("investAssets")
public class InvestAsset {

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
	
	@Builder
	public InvestAsset(Long id, String assetTitle, Long assetTotalAmount, Long assetCurrentAmount,
			Long investorNumbers, String assetStatus, Date assetValidFromDate, Date assetValidToDate) {
		this.id = id;
		this.assetTitle = assetTitle;
		this.assetTotalAmount = assetTotalAmount;
		this.assetCurrentAmount = assetCurrentAmount;
		this.investorNumbers = investorNumbers;
		this.assetStatus = assetStatus;
		this.assetValidFromDate = assetValidFromDate;
		this.assetValidToDate = assetValidToDate;
	}

}
