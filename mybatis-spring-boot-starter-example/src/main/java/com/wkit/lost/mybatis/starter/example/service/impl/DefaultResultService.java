package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractUniformServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.mapper.ResultMapper;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import org.springframework.stereotype.Service;

@Service
public class DefaultResultService extends AbstractUniformServiceExecutor<ResultMapper, Result, Long>
        implements ResultService {
}
