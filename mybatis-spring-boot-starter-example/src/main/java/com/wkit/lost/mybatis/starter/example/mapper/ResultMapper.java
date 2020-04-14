package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.UniformMapperExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultMapper extends UniformMapperExecutor<Result, Long> {
}
