package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.starter.example.beans.Class;
import com.wkit.lost.mybatis.starter.example.vo.ClassVo;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassMapper extends MapperExecutor<Class, Long, ClassVo> {
}
