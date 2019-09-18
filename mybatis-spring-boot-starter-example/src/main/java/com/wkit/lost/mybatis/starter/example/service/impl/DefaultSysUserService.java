package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceExecutor;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.mapper.SysUserMapper;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class DefaultSysUserService extends AbstractServiceExecutor<SysUserMapper, SysUser, SysUser> implements SysUserService {
    
}
