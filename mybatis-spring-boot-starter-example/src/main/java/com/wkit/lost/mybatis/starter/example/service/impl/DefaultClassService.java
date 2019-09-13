package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceExecutor;
import com.wkit.lost.mybatis.starter.example.beans.Class;
import com.wkit.lost.mybatis.starter.example.mapper.ClassMapper;
import com.wkit.lost.mybatis.starter.example.service.ClassService;
import com.wkit.lost.mybatis.starter.example.vo.ClassVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultClassService extends AbstractServiceExecutor<ClassMapper, Class, Long, ClassVo> implements ClassService {
}
