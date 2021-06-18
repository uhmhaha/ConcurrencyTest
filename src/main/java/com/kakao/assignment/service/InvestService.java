package com.kakao.assignment.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.assignment.dto.InvestAssetReqDto;
import com.kakao.assignment.dto.InvestAssetRespDto;
import com.kakao.assignment.dto.InvestReqDto;
import com.kakao.assignment.dto.MyInvestRespDto;
import com.kakao.assignment.repository.InvestAssetRepository;
import com.kakao.assignment.repository.InvestRepository;
import com.kakao.assignment.vo.Invest;
import com.kakao.assignment.vo.InvestAsset;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestService {
	
	private final InvestAssetRepository investAssetRepository;
	private final InvestRepository investRepository;
	private final RedisTemplate<?, ?> redisTemplate;
	
	@Transactional
	public Long saveInvestAsset(InvestAssetReqDto req) {
		
		return investAssetRepository.save(req.toRedisHash()).getId(); 
	}
	 
	public InvestAssetRespDto getInvestAsset(Long id ) {
		InvestAsset investAsset = investAssetRepository.findById(id).
				orElseThrow(() -> new IllegalArgumentException("There is no data : id =>"+id));
		return new InvestAssetRespDto(investAsset);
	}
	
	public List<InvestAssetRespDto> getInvestAssets() {
		List<InvestAsset> list = investAssetRepository.findAll();
		return list
				.stream()
				.map( investAsset -> new InvestAssetRespDto(investAsset))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public Long saveInvest(InvestReqDto req, String userId) throws DataAccessException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			
			@Override
			public List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
				
				
				String hashKey = "investAssets:".concat(String.valueOf(req.getAssetId()));
				System.out.println("key : " + hashKey);

				redisOperations.watch(hashKey);
				
				InvestAsset a = investAssetRepository.findById(req.getAssetId()).
						orElseThrow(() -> new IllegalArgumentException("There is no data : id =>"+req.getAssetId()));
				
//				try {
					
					redisOperations.multi();	
					if( a.getAssetTotalAmount() >= a.getAssetCurrentAmount() + req.getInvestAmount()
						&& a.getAssetValidFromDate().compareTo(req.getInvestDate()) >= 0 
						&& a.getAssetStatus().equals("INPROGRESS") )
					{
						Map<String, Object> val = new HashMap<String, Object>();
						val.put("assetId", req.getAssetId());
						val.put("investAmount", req.getInvestAmount());
						val.put("userId", req.getUserId());
						val.put("investDate", LocalDateTime.now().format(formatter));
						
//						InvestReqDto inv = new InvestReqDto().builder()
//											.assetId(req.getAssetId())
//											.investAmount(req.getInvestAmount())
//											.investDate(LocalDateTime.now())
//											.userId(req.getUserId())
//											.build();
											
						String key = "invest:".concat(userId).concat(":").concat(String.valueOf(req.getAssetId()));
	
						redisOperations.opsForHash().putAll(key, val);
						//investRepository.save(req.toRedisHash()).getAssetId();
					}
//		        } catch (Exception e) {
//		            redisOperations.discard();
//		            return null;
//		        }
				
				return redisOperations.exec(); // EXEC : commit
			}
		});
		
		return req.getAssetId();
		
	}

	public List<MyInvestRespDto> getMyInvestRecord(String userId ) {

		List<Invest> invests = investRepository.findByUserId(userId).
				orElseThrow(() -> new IllegalArgumentException("There is no invest record : user-id => "+userId));
		
		List<Long> assetIds = invests.stream()
									.map( invest -> invest.getAssetId())
									.collect(Collectors.toList());
		
		List<InvestAsset> investAssets = investAssetRepository.findByAssetId(assetIds);
		
		return invests
				.stream()
				.map( invest -> {
					Optional<InvestAsset> investAsset = investAssets.stream().filter(asset -> invest.getAssetId().equals(asset.getId())).findFirst();
					
					return new MyInvestRespDto(investAsset.get(), invest);
				})
				.collect(Collectors.toList());
	}
}
