package com.wvkity.mybatis.starter.example.mapper;

import com.wvkity.mybatis.mapper.BaseMapper;
import com.wvkity.mybatis.starter.example.entity.Grade;
import com.wvkity.mybatis.starter.example.vo.GradeVo;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeMapper extends BaseMapper<Grade, GradeVo, Long> {
}
