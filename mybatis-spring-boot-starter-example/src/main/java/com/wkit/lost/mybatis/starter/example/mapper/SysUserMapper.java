package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends MapperExecutor<SysUser, SysUser> {
}
