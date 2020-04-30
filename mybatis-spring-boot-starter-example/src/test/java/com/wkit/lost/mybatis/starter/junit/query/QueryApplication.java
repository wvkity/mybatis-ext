package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.example.vo.UserVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import com.wkit.lost.paging.Pageable;
import com.wkit.lost.paging.Pager;
import com.wkit.lost.paging.PagerWrap;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class QueryApplication extends RootTestRunner {

    @Inject
    private UserService userService;
    @Inject
    private ResultService resultService;

    @Test
    public void test() {
        List<Result> result = resultService.list(1L, 2L, 3L);
        log.info("执行结果: {}", result);
    }

    @Test
    public void existsTest() {
        User u = new User();
        u.setId(1L).setSex(1).setState(2);
        boolean result = userService.exists(u);
        log.info("执行结果: {}", result);
    }

    @Test
    public void existsByIdTest() {
        boolean result = userService.existsById(2L);
        log.info("执行结果: {}", result);
    }

    @Test
    public void existsByCriteriaTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getId, 2L);
        boolean result = userService.exists(criteria);
        log.info("执行结果: {}", result);
    }

    @Test
    public void countTest() {
        User u = new User();
        u.setId(2L).setSex(1).setState(1);
        long result = userService.count(u);
        log.info("执行结果: {}", result);
    }

    @Test
    public void countByCriteriaTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getSex, 1, User::getPassword, "123456a");
        long result = userService.count(criteria);
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

    @Test
    public void listByEntityTest() {
        User u = new User();
        u.setId(2L).setSex(1).setState(1);
        List<User> result = userService.list(u);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void listByEntitiesTest() {
        User u1 = new User();
        u1.setId(1L).setSex(3).setScore(88);
        User u2 = new User();
        u2.setId(2L).setSex(1).setState(1);
        User u3 = new User();
        u3.setId(3L).setSex(2).setUserName("张三");
        List<User> result = userService.listByEntities(u1, u2, u3);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void pageableListTest() {
        User u1 = new User();
        u1.setSex(1).setPassword("123456a");
        List<User> result = userService.list(u1, new Pager());
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void pageableListByCriteriaTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getSex, 1, User::getPassword, "123456a");
        Pageable pageable = new PagerWrap<User>();
        List<User> result = userService.list(criteria, pageable);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
        log.info("pageable对象值: {}", JSON.toJSONString(pageable, true));
    }

    @Test
    public void arrayListTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getSex, 1, User::getPassword, "123456a");
        List<Object[]> result = userService.array(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void objectListTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getSex, 1, User::getPassword, "123456a");
        List<Map<String, Object>> result = userService.map(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void mapListTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getSex, 1, User::getPassword, "123456a");
        List<Object> result = userService.objects(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void customTest() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.eq(User::getSex, 1, User::getPassword, "123456a");
        List<UserVo> result = userService.custom(criteria.resultType(UserVo.class));
        log.info("结果类型: {}", result.isEmpty() ? null : result.get(0).getClass());
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void nestedTest1() {
        QueryCriteria<User> criteria = new QueryCriteria<>(User.class);
        criteria.and(it -> it.eq(User::getSex, 1, User::getState, 1)).orIdEq(1L);
        List<User> result = userService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void nestedTest2() {
        QueryCriteria<User> criteria = QueryCriteria.from(User.class);
        //criteria.leftJoin(Student.class);
        criteria.eq(User::getDeleted, false).and(it ->
                it.and(i -> i.eq(User::getState, 2, User::getCreatedUserName, "root", User::getVersion, 3))
                        .or(i -> i.eq(User::getState, 1, User::getCreatedUserName, "system")));
        List<User> result = userService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }
}
