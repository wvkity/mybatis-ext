package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends MapperExecutor<Student, StudentVo> {
}
