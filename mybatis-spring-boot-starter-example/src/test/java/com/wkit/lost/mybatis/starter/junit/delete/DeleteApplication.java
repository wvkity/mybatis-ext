package com.wkit.lost.mybatis.starter.junit.delete;

import com.wkit.lost.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class DeleteApplication extends RootTestRunner {

    @Inject
    private UserService userService;

    @Inject
    private GradeService gradeService;

    @Test
    public void deleteTest() {
        User user = new User();
        user.setId(1L).setVersion(10).setSex(1).setState(2).setUserName("张三");
        int result = userService.delete(user);
        log.info("执行结果: {}", result);
    }

    @Test
    public void deleteByIdTest() {
        int result = userService.delete(5L);
        log.info("执行结果: {}", result);
    }

    @Test
    public void deleteByCriteriaTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.idEq(1L).eq(User::getSex, 1, User::getState, 2, User::getDeleted, false);
        int result = userService.delete(criteria);
        log.info("执行结果: {}", result);
    }

    @Test
    public void batchDeleteByIdTest() {
        int result = userService.batchDelete(5L, 6L, 7L);
        log.info("执行结果: {}", result);
    }

    @Test
    public void batchDeleteTest() {
        User u1 = new User();
        u1.setId(100L).setState(2).setSex(1).setVersion(1);
        User u2 = new User();
        u2.setId(101L).setState(1).setSex(0).setDeleted(false);
        User u3 = new User();
        u3.setId(102L).setState(3).setSex(1).setDeleted(true);
        User u4 = new User();
        u4.setId(103L).setState(4).setSex(0).setVersion(11);
        int result = userService.batchDelete(u1, u2, u3, u4);
        log.info("执行结果: {}", result);
    }

    @Test
    public void logicDeleteTest() {
        User u1 = new User();
        u1.setId(100L).setState(2).setSex(1).setVersion(1);
        int result = userService.logicDelete(u1);
        log.info("执行结果: {}", result);
    }

    @Test
    public void logicDeleteTest2() {
        Grade g1 = new Grade();
        g1.setId(100L);
        int result = gradeService.logicDelete(g1);
        log.info("执行结果: {}", result);
    }

    @Test
    public void logicDeleteByCriteriaTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getId, 4L, User::getSex, 3, User::getState, 5);
        int result = userService.logicDelete(criteria);
        log.info("执行结果: {}", result);
    }
}
