package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class OrderApplication extends RootTestRunner {

    @Inject
    private UserService userService;
    @Inject
    private ResultService resultService;

    @SuppressWarnings("unchecked")
    @Test
    public void ascTest1() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.and(it -> it.eq(User::getSex, 1, User::getState, 1)).orIdEq(1L);
        criteria.asc(User::getState).asc("version").desc(User::getScore).directAscWithAlias("st_", "GMT_CREATED").as("st_");
        criteria.group(User::getState, User::getScore);
        List<User> result = userService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }
}
