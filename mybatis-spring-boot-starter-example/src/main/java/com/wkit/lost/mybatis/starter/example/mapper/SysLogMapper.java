package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.starter.example.beans.SysLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogMapper extends MapperExecutor<SysLog, Long, SysLog> {
}
