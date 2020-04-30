package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherMapper extends MapperExecutor<Teacher> {
}
