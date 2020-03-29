package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractBaseServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.mapper.ResultMapper;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import com.wkit.lost.mybatis.starter.example.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultResultService extends AbstractBaseServiceExecutor<ResultMapper, Result, ResultVo> implements ResultService {
}
