package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Log4j2
public class QueryApplication extends RootTestRunner {

    @Inject
    private UserService userService;

    @Test
    public void existsTest() {
        User u = new User();
        u.setId(1L).setSex(1).setState(2);
        boolean result = userService.exists(u);
        log.info("执行结果: {}", result);
    }

    @Test
    public void existsByIdTest() {
        boolean result = userService.exists(2L);
        log.info("执行结果: {}", result);
    }

    @Test
    public void queryByEntityTest() {
        User u = new User();
        u.setId(2L).setSex(1).setState(1);
        List<User> result = userService.list(u);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void countTest() {
        User u = new User();
        u.setId(2L).setSex(1).setState(1);
        long result = userService.count(u);
        log.info("执行结果: {}", result);
    }

    @Test
    public void selectOneTest() {
        Optional<User> optional = userService.selectOne(2L);
        if (optional.isPresent()) {
            log.info("执行结果: {}", JSON.toJSONString(optional.get(), true));
        } else {
            log.info("执行结果: empty!");
        }
    }

    @Test
    public void listTest() {
        List<User> result = userService.list(1L, 2L, 3L, 4L);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }
}
