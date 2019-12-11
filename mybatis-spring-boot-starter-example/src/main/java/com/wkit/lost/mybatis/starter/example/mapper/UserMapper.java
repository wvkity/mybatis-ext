package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.UnifyMapperExecutor;
import com.wkit.lost.mybatis.starter.example.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends UnifyMapperExecutor<User> {
}
