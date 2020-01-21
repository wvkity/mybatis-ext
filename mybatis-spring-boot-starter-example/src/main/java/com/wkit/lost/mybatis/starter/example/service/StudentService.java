package com.wkit.lost.mybatis.starter.example.service;

import com.wkit.lost.mybatis.service.ServiceExecutorCallable;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;

public interface StudentService extends ServiceExecutorCallable<Student, StudentVo> {
}
