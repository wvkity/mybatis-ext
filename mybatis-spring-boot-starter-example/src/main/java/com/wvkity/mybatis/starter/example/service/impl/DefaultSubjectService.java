package com.wvkity.mybatis.starter.example.service.impl;

import com.wvkity.mybatis.service.AbstractSerialService;
import com.wvkity.mybatis.starter.example.entity.Subject;
import com.wvkity.mybatis.starter.example.mapper.SubjectMapper;
import com.wvkity.mybatis.starter.example.service.SubjectService;
import com.wvkity.mybatis.starter.example.vo.SubjectVo;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultSubjectService extends AbstractSerialService<SubjectMapper, Subject, SubjectVo> implements SubjectService {
}
