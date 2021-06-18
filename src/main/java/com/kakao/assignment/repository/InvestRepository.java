package com.kakao.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kakao.assignment.vo.Invest;

public interface InvestRepository extends CrudRepository<Invest, Long>{
    
	Optional<List<Invest>> findByUserId(String userId);
	
    
    //List<InvestInfo> findByEmailIdInAndPincodeIn(List<String> emails, List<String> pinCodes);
}
