package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceExecutorCallable;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.mapper.StudentMapper;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultStudentService extends AbstractServiceExecutorCallable<StudentMapper, Student, StudentVo> implements StudentService {
}
