package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceExecutor;
import com.wkit.lost.mybatis.starter.example.beans.School;
import com.wkit.lost.mybatis.starter.example.mapper.SchoolMapper;
import com.wkit.lost.mybatis.starter.example.service.SchoolService;
import com.wkit.lost.mybatis.starter.example.vo.SchoolVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultSchoolService extends AbstractServiceExecutor<SchoolMapper, School, SchoolVo> implements SchoolService {
}
