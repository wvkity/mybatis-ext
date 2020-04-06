package com.wkit.lost.mybatis.starter.example.service;

import com.wkit.lost.mybatis.service.ServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.User;

import java.util.List;

public interface UserService extends ServiceExecutor<User> {

    int testSave( Grade grade, List<User> users );
}
