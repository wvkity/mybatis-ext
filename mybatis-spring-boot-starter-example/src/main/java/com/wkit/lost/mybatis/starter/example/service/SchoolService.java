package com.wkit.lost.mybatis.starter.example.service;

import com.wkit.lost.mybatis.service.ServiceExecutor;
import com.wkit.lost.mybatis.starter.example.beans.School;
import com.wkit.lost.mybatis.starter.example.vo.SchoolVo;

public interface SchoolService extends ServiceExecutor<School, String, SchoolVo> {
}
