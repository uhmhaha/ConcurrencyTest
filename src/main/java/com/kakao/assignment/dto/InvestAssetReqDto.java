package com.kakao.assignment.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.kakao.assignment.vo.InvestAsset;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvestAssetReqDto {

	//상품 ID, 상품 제목, 총 모집금액, 현재 모집금액, 투자자 수, 투자모집상태(모집중, 모집 완료), 상품 모집기간
	@Id
	private Long id;
	private String assetTitle;
	private Long assetTotalAmount;
	private Long assetCurrentAmount;
	private Long investorNumbers;
	private String assetStatus;
	private LocalDateTime assetValidFromDate;
	private LocalDateTime assetValidToDate;
	
	@Builder
	public InvestAssetReqDto(Long id, String assetTitle, Long assetTotalAmount, Long assetCurrentAmount,
			Long investorNumbers, String assetStatus, LocalDateTime assetValidFromDate,LocalDateTime assetValidToDate) {
		
		this.id = id;
		this.assetTitle = assetTitle;
		this.assetTotalAmount = assetTotalAmount;
		this.assetCurrentAmount = assetCurrentAmount;
		this.investorNumbers = investorNumbers;
		this.assetStatus = assetStatus;
		this.assetValidFromDate = assetValidFromDate;
		this.assetValidToDate = assetValidToDate;
	}
	
	public InvestAsset toRedisHash() {
		
		return InvestAsset.builder()
				.id(id)
				.assetTitle(assetTitle)
				.assetTotalAmount(assetTotalAmount)
				.assetCurrentAmount(assetCurrentAmount)
				.investorNumbers(investorNumbers)
				.assetStatus(assetStatus)
				.assetValidFromDate(assetValidFromDate)
				.assetValidToDate(assetValidToDate)
				.build();
	}
	
	
}
