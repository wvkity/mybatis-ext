package com.wvkity.mybatis.starter.example.service.impl;

import com.wvkity.mybatis.service.AbstractSerialService;
import com.wvkity.mybatis.starter.example.entity.Student;
import com.wvkity.mybatis.starter.example.mapper.StudentMapper;
import com.wvkity.mybatis.starter.example.service.StudentService;
import com.wvkity.mybatis.starter.example.vo.StudentVo;
import org.springframework.stereotype.Service;

@Service
public class DefaultStudentService extends AbstractSerialService<StudentMapper, Student, StudentVo> implements StudentService {
}
