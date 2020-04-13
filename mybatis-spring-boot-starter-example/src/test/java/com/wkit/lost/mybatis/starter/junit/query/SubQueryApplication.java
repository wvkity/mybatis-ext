package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.wrapper.criteria.QueryCriteria;
import com.wkit.lost.mybatis.starter.example.entity.BaseEntity;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.example.vo.StudentVo;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class SubQueryApplication extends RootTestRunner {
    
    @Inject
    private UserService userService;
    
    @Inject
    private StudentService studentService;
    
    @Test
    public void idEqTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.idEq(sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void equalTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.eq("id", cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void notEqualTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.ne("id", cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void lessThanTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.lt("id", cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void lessThanAndEqualTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.le("id", cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void greaterThanTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.gt("id", cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void greaterThanAndEqualTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.ge(BaseEntity::getId, cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void likeTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.propertyAutoMappingAlias().as("us__").sub(User.class, (it, cs) -> {
            cs.select(User::getUserName).eq(User::getId, 2L, User::getState, 1);
            it.like(Student::getName, cs);
        }).as("st__");
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void notLikeTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getUserName).eq(User::getId, 2L, User::getState, 1);
            it.notLike(Student::getName, cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void inTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, cs) -> {
            cs.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.in(Student::getId, cs);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }
}
