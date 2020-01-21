package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceExecutorCallable;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.mapper.GradeMapper;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.vo.GradeVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultGradeService extends AbstractServiceExecutorCallable<GradeMapper, Grade, GradeVo> implements GradeService {

}
