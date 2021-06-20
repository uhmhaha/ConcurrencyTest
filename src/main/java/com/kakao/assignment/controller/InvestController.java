package com.kakao.assignment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.assignment.dto.InvestAssetReqDto;
import com.kakao.assignment.dto.InvestAssetRespDto;
import com.kakao.assignment.dto.InvestReqDto;
import com.kakao.assignment.dto.MyInvestRespDto;
import com.kakao.assignment.service.InvestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class InvestController {

	private final InvestService investService;

	// for livenessProbe
	@GetMapping("/")
	public String Welcome() {
		return "OK";
	}

	// 1. 전체 투자 상품 조회 APIs
	/**
	 * 투자상품등록
	 * 
	 * @param reqDto
	 * @return
	 */
	@PostMapping("/investAsset")
	public Long saveInvestAsset(@RequestBody InvestAssetReqDto reqDto) {
		log.info(" >>>>>>>>>>>> [saveInvestAsset]");
		return investService.saveInvestAsset(reqDto);
	}

	/**
	 * 투자상품 단건 조회
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/investAsset/{id}")
	public InvestAssetRespDto getInvestAsset(@PathVariable Long id) {
		log.info(" >>>>>>>>>>>> [getInvestAsset] = {}", id);
		return investService.getInvestAsset(id);
	}

	/**
	 * 투자상품 다건 조회
	 * 
	 * @return
	 */

	@GetMapping("/investAssets")
	public List<InvestAssetRespDto> getInvestAssets() {
		log.info(" >>>>>>>>>>>> [getInvestAssets] ");
		return investService.getInvestAssets();
	}

	// 2. 투자하기 API
	/**
	 * 투자하기
	 * 
	 * @param userId
	 * @param reqDto
	 * @return
	 */
	@PostMapping("/invest")
	public Long saveInvest(@RequestHeader("X-USER-ID") String userId,
			@RequestBody InvestReqDto reqDto) {

		log.info(" >>>>>>>>>>>> [saveInvest]");

		return investService.saveInvest(reqDto, userId);
	}

	// 3. 나의 투자 정보져오기 API
	/**
	 * 나의 투자정보 가져오기
	 * 
	 * @param userId
	 * @return
	 */

	@GetMapping("/invest/record")
	public List<MyInvestRespDto> getMyInvestRecord(@RequestHeader("X-USER-ID") String userId) {
		
		log.info(" >>>>>>>>>>>> [getMyInvestRecord]");
		
		return investService.getMyInvestRecord(userId);

	}
}
