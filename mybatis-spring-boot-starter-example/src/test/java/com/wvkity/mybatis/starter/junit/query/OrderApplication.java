package com.wvkity.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wvkity.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wvkity.mybatis.starter.example.entity.User;
import com.wvkity.mybatis.starter.example.service.ExamService;
import com.wvkity.mybatis.starter.example.service.UserService;
import com.wvkity.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class OrderApplication extends RootTestRunner {

    @Inject
    private UserService userService;
    @Inject
    private ExamService examService;

    @SuppressWarnings("unchecked")
    @Test
    public void ascTest1() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.and(it -> it.eq(User::getSex, 1, User::getState, 1)).orIdEq(1L);
        criteria.asc(User::getState).asc("version").ascWithAlias("st_", "GMT_CREATED").as("st_");
        criteria.groups(User::getState);
        List<User> result = userService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }
}
