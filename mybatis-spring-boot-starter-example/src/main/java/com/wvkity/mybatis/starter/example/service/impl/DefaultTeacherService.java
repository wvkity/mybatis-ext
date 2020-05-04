package com.wvkity.mybatis.starter.example.service.impl;

import com.wvkity.mybatis.service.AbstractSimpleService;
import com.wvkity.mybatis.starter.example.entity.Teacher;
import com.wvkity.mybatis.starter.example.mapper.TeacherMapper;
import com.wvkity.mybatis.starter.example.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class DefaultTeacherService extends AbstractSimpleService<TeacherMapper, Teacher> implements TeacherService {
}
