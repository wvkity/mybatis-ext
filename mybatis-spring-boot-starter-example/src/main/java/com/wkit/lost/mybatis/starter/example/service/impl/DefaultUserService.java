package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractUnifyServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.mapper.UserMapper;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService extends AbstractUnifyServiceExecutor<UserMapper, User> implements UserService {
}
