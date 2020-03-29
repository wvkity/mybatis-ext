package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.BaseMapperExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Subject;
import com.wkit.lost.mybatis.starter.example.vo.SubjectVo;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectMapper extends BaseMapperExecutor<Subject, SubjectVo> {
}
