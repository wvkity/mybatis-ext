package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceExecutor;
import com.wkit.lost.mybatis.starter.example.beans.Teacher;
import com.wkit.lost.mybatis.starter.example.mapper.TeacherMapper;
import com.wkit.lost.mybatis.starter.example.service.TeacherService;
import com.wkit.lost.mybatis.starter.example.vo.TeacherVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultTeacherService extends AbstractServiceExecutor<TeacherMapper, Teacher, TeacherVo> implements TeacherService {
}
