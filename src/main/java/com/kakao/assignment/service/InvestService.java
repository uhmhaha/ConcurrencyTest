package com.kakao.assignment.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.assignment.dto.InvestAssetReqDto;
import com.kakao.assignment.dto.InvestAssetRespDto;
import com.kakao.assignment.dto.InvestReqDto;
import com.kakao.assignment.dto.MyInvestRespDto;
import com.kakao.assignment.exception.SoldoutException;
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
	//private final RedisTemplate<?, ?> redisTemplate;
	private final StringRedisTemplate redisTemplate;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	
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
	public Long saveInvest(InvestReqDto req, String userId) {
		
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public List<Object> execute(@SuppressWarnings("rawtypes") RedisOperations redisOperations)  {
				
				
				String hashKey = "investAssets:".concat(String.valueOf(req.getAssetId()));
				System.out.println("key : " + hashKey);

				redisOperations.watch(hashKey);
				
				InvestAsset a = investAssetRepository.findById(req.getAssetId()).
						orElseThrow(() -> new IllegalArgumentException("There is no data : id =>"+req.getAssetId()));
				
				try {
					
					redisOperations.multi();	
					if( a.getAssetTotalAmount() >= a.getAssetCurrentAmount() + req.getInvestAmount())
//						&& a.getAssetValidFromDate().compareTo(req.getInvestDate()) >= 0 
//						&& a.getAssetStatus().equals("INPROGRESS") )
					{	
						
						redisOperations.opsForHash().put("investAssets:".concat(String.valueOf(a.getId()))
														  , "assetCurrentAmount"
														  , String.valueOf(a.getAssetCurrentAmount() + req.getInvestAmount()));
						
						Map<String, String> val2 = new HashMap<String, String>();
						val2.put("assetId", req.getAssetId().toString());
						val2.put("investAmount", req.getInvestAmount().toString());
						val2.put("userId", req.getUserId());
						val2.put("investDate", LocalDateTime.now().format(formatter));
											
						String key = "invest:".concat(userId).concat(":").concat(String.valueOf(req.getAssetId()));
						redisOperations.opsForHash().putAll(key, val2);
						
					} else {
						throw new SoldoutException();
					}
		        } catch (DataAccessException e) {
		            redisOperations.discard();
		            return null;
		        }
				
				return redisOperations.exec(); // EXEC : commit
			}
		});
		
		return req.getAssetId();
		
	}

	public List<MyInvestRespDto> getMyInvestRecord(String userId ) {
		
		Set<String> redisKeys = redisTemplate.keys("invest:".concat(userId).concat(":*"));
		
		List<Invest> invests = new ArrayList<Invest>();
		
		Field[] fields = Invest.class.getDeclaredFields();

		for( String key : redisKeys) {
			
			List<Object> i = redisTemplate.opsForHash().multiGet(key, Arrays.stream(fields)
															.map(field -> field.getName())
															.collect(Collectors.toList()));
			Invest inv = Invest.builder()
				.assetId(Long.valueOf(String.valueOf(i.get(1))))
				.investDate(LocalDateTime.parse(String.valueOf(i.get(3)), formatter))
				.investAmount(Long.valueOf(String.valueOf(i.get(2))))
				.build();
					
			invests.add(inv);
		}
		
		List<Long> assetIds = invests.stream()
									.map( invest -> invest.getAssetId())
									.collect(Collectors.toList());
		
		List<InvestAsset> investAssets = investAssetRepository.findAllById(assetIds);
		
		return invests
				.stream()
				.map( invest -> {
					Optional<InvestAsset> investAsset = investAssets.stream().filter(asset -> invest.getAssetId().equals(asset.getId())).findFirst();
					
					return new MyInvestRespDto(investAsset.get(), invest);
				})
				.collect(Collectors.toList());
	}

}
