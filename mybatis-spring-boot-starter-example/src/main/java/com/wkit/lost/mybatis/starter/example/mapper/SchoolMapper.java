package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.starter.example.beans.School;
import com.wkit.lost.mybatis.starter.example.vo.SchoolVo;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolMapper extends MapperExecutor<School, SchoolVo> {
}
