package com.wkit.lost.mybatis.starter.junit.insert;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class InsertApplication extends RootTestRunner {

    @Inject
    private GradeService gradeService;
    @Inject
    private UserService userService;

    @Test
    public void singleInsertTest() {
        Grade grade = new Grade();
        grade.setName("S7").setCreatedUserId(DEF_SYS_USER_ID).setCreatedUserName(DEF_SYS_USER_NAME)
                .setGmtCreated(LocalDateTime.now());
        int result = gradeService.save(grade);
        log.info("执行结果: {}", result);
    }

    @Test
    public void singleInsertWithNotNullTest() {
        Grade grade = new Grade();
        grade.setName("S9").setCreatedUserId(DEF_SYS_USER_ID).setCreatedUserName(DEF_SYS_USER_NAME)
                .setGmtCreated(LocalDateTime.now());
        int result = gradeService.saveNotWithNull(grade);
        log.info("执行结果: {}", result);
    }

    @Test
    public void saveTest() {
        User user = new User();
        user.setUserName("兮兮").setPassword("123456test")
                .setState(2).setScore(65).setSex(1).setVersion(1).setDeleted(false);
        int result = userService.save(user);
        log.info("执行结果: {}", result);
    }

    @Test
    public void batchInsertTest() {
        Grade grade = new Grade();
        grade.setName("S1").setCreatedUserId(DEF_SYS_USER_ID).setCreatedUserName(DEF_SYS_USER_NAME)
                .setGmtCreated(LocalDateTime.now());
        Grade g2 = new Grade();
        g2.setName("S3").setCreatedUserId(DEF_SYS_USER_ID)
                .setCreatedUserName(DEF_SYS_USER_NAME).setGmtCreated(LocalDateTime.now());
        List<Grade> list = ArrayUtil.toList(grade, g2);
        int result = gradeService.batchSave(list);
        log.info("执行结果: {}", result);
    }

    @Test
    public void batchInsertNotWithAuditTest() {
        Grade grade = new Grade();
        grade.setName("V1").setCreatedUserId(DEF_SYS_USER_ID).setCreatedUserName(DEF_SYS_USER_NAME)
                .setGmtCreated(LocalDateTime.now());
        Grade g2 = new Grade();
        g2.setName("T2").setCreatedUserId(DEF_SYS_USER_ID)
                .setCreatedUserName(DEF_SYS_USER_NAME).setGmtCreated(LocalDateTime.now());
        List<Grade> list = ArrayUtil.toList(grade, g2);
        int result = gradeService.batchSaveNotWithAudit(list);
        log.info("执行结果: {}", result);
        log.info("执行后的数据: {}", JSON.toJSONString(list, true));
    }
}
