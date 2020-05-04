package com.wvkity.mybatis.starter.example.mapper;

import com.wvkity.mybatis.mapper.SimpleMapper;
import com.wvkity.mybatis.starter.example.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends SimpleMapper<User> {
}
