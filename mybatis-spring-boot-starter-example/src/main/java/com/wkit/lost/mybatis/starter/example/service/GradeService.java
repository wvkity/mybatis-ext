package com.wkit.lost.mybatis.starter.example.service;

import com.wkit.lost.mybatis.service.ServiceExecutorCallable;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.vo.GradeVo;

public interface GradeService extends ServiceExecutorCallable<Grade, GradeVo> {
}
