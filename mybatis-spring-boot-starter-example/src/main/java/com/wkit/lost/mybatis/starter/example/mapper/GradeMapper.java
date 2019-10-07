package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.vo.GradeVo;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeMapper extends MapperExecutor<Grade, GradeVo> {
}
