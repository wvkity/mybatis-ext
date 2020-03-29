package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.BaseMapperExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.vo.ResultVo;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultMapper extends BaseMapperExecutor<Result, ResultVo> {
}
