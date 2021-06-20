package com.kakao.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kakao.assignment.vo.InvestAsset;

public interface InvestAssetRepository extends CrudRepository<InvestAsset, Long>{
    @Override
    List<InvestAsset> findAll();
    
    @Override
    List<InvestAsset> findAllById(Iterable<Long> ids);
    
}
