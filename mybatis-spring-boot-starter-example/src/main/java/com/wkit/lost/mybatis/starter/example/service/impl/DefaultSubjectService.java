package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractSerialServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Subject;
import com.wkit.lost.mybatis.starter.example.mapper.SubjectMapper;
import com.wkit.lost.mybatis.starter.example.service.SubjectService;
import com.wkit.lost.mybatis.starter.example.vo.SubjectVo;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultSubjectService extends AbstractSerialServiceExecutor<SubjectMapper, Subject, SubjectVo> implements SubjectService {
}
