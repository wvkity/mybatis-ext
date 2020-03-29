package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractBaseServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Subject;
import com.wkit.lost.mybatis.starter.example.mapper.SubjectMapper;
import com.wkit.lost.mybatis.starter.example.service.SubjectService;
import com.wkit.lost.mybatis.starter.example.vo.SubjectVo;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultSubjectService extends AbstractBaseServiceExecutor<SubjectMapper, Subject, SubjectVo> implements SubjectService {
}
