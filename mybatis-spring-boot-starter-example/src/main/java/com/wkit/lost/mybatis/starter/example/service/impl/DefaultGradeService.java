package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractBaseServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.mapper.GradeMapper;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.vo.GradeVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultGradeService extends AbstractBaseServiceExecutor<GradeMapper, Grade, GradeVo, Long> 
        implements GradeService {

}
