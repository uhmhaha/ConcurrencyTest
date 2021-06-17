package com.kakao.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@ActiveProfiles("test")
class RedisConfigTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Test
	public void testString() {
		final String key = "ConnectionStatus";
		final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
		
		stringStringValueOperations.set(key, "Success");
		
		final String result = stringStringValueOperations.get(key);
		
		System.out.print("result : " + result);
	}
}
