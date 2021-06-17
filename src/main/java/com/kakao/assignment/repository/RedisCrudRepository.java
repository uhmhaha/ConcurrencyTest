package com.kakao.assignment.repository;

import org.springframework.data.repository.CrudRepository;

import com.kakao.assignment.vo.InvestAsset;

public interface RedisCrudRepository extends CrudRepository<InvestAsset, Long>{

}
