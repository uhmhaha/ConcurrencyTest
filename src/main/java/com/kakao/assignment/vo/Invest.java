package com.kakao.assignment.vo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@RedisHash("invest")
public class Invest {

	// 상품 ID, 상품 제목, 총 모집금액, 나의 투자금액, 투자일시
	@Id
	private String userId;
	@Indexed
	private Long assetId;
	private Long investAmount;
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	private LocalDateTime investDate;

	@Builder
	public Invest(String userId, Long assetId, Long investAmount,@JsonDeserialize(using=LocalDateTimeDeserializer.class) LocalDateTime investDate) {
		//this.investId = investId;
		this.userId = userId;
		this.assetId = assetId;
		this.investAmount = investAmount;
		this.investDate = investDate;
	}

}
