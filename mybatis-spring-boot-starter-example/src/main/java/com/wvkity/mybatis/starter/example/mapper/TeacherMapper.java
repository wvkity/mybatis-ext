package com.wvkity.mybatis.starter.example.mapper;

import com.wvkity.mybatis.mapper.SimpleMapper;
import com.wvkity.mybatis.starter.example.entity.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherMapper extends SimpleMapper<Teacher> {
}
