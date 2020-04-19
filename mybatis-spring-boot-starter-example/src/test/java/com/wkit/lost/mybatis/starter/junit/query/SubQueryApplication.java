package com.wkit.lost.mybatis.starter.junit.query;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.wrapper.criteria.QueryCriteria;
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
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1, User::getVersion, 1);
            it.idEq(sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void equalTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.eq("id", sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void notEqualTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.ne("id", sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void lessThanTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.lt("id", sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void lessThanAndEqualTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.le("id", sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void greaterThanTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.gt("id", sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void greaterThanAndEqualTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.ge("id", sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void likeTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.propertyAutoMappingAlias().as("us__").sub(User.class, (it, sc) -> {
            sc.select(User::getUserName).eq(User::getId, 2L, User::getState, 1);
            it.like(Student::getName, sc);
        }).as("st__");
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void notLikeTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getUserName).eq(User::getId, 2L, User::getState, 1);
            it.notLike(Student::getName, sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void inTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.sub(User.class, (it, sc) -> {
            sc.select(User::getId).eq(User::getId, 2L, User::getState, 1);
            it.in(Student::getId, sc);
        });
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void existsTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.useAs().sub(User.class, (it, sc) -> {
            sc.select(User::getId).normalEq(it, Student::getId);
            it.exists(sc);
        }).useAs();
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }

    @Test
    public void notExistsTest() {
        QueryCriteria<Student> criteria = new QueryCriteria<>(Student.class);
        criteria.as("st_").propertyAutoMappingAlias().sub(User.class, (it, sc) -> {
            sc.directSelect("id", "u_id").normalEq(it, Student::getId);
            it.notExists(sc);
        }).as("ur_");
        List<StudentVo> result = studentService.list(criteria);
        log.info("执行结果: {}", JSON.toJSONString(result, true));
    }
}
