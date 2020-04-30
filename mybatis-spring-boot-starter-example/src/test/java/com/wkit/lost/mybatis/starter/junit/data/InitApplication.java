package com.wkit.lost.mybatis.starter.junit.data;

import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.Result;
import com.wkit.lost.mybatis.starter.example.entity.Student;
import com.wkit.lost.mybatis.starter.example.entity.Subject;
import com.wkit.lost.mybatis.starter.example.entity.Teacher;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.service.ResultService;
import com.wkit.lost.mybatis.starter.example.service.StudentService;
import com.wkit.lost.mybatis.starter.example.service.SubjectService;
import com.wkit.lost.mybatis.starter.example.service.TeacherService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class InitApplication extends RootTestRunner {

    @Inject
    private UserService userService;
    @Inject
    private GradeService gradeService;
    @Inject
    private TeacherService teacherService;
    @Inject
    private SubjectService subjectService;
    @Inject
    private StudentService studentService;
    @Inject
    private ResultService resultService;

    private static List<User> USERS;
    private static List<Teacher> TEACHERS;
    private static List<Grade> GRADES;
    private static List<Subject> SUBJECTS;
    private static List<Student> STUDENTS;
    private static List<Result> RESULTS;
    private static final Long DEFAULT_USER_ID = 1L;
    private static final String DEFAULT_USER_NAME = "system";
    private static final LocalDateTime DEFAULT_TIME;

    static {
        DEFAULT_TIME = LocalDateTime.ofEpochSecond(1219551764L, 0, ZoneOffset.UTC);
    }

    @Test
    public void init() {
        dataInit();
    }

    private void dataInit() {
    }
}
