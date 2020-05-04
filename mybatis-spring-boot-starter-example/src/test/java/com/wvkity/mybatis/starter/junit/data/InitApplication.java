package com.wvkity.mybatis.starter.junit.data;

import com.wvkity.mybatis.starter.example.entity.Exam;
import com.wvkity.mybatis.starter.example.entity.Grade;
import com.wvkity.mybatis.starter.example.entity.Klass;
import com.wvkity.mybatis.starter.example.entity.Relevance;
import com.wvkity.mybatis.starter.example.entity.Student;
import com.wvkity.mybatis.starter.example.entity.Subject;
import com.wvkity.mybatis.starter.example.entity.Teacher;
import com.wvkity.mybatis.starter.example.entity.User;
import com.wvkity.mybatis.starter.example.service.ExamService;
import com.wvkity.mybatis.starter.example.service.GradeService;
import com.wvkity.mybatis.starter.example.service.StudentService;
import com.wvkity.mybatis.starter.example.service.SubjectService;
import com.wvkity.mybatis.starter.example.service.TeacherService;
import com.wvkity.mybatis.starter.example.service.UserService;
import com.wvkity.mybatis.starter.example.utils.JsonUtil;
import com.wvkity.mybatis.starter.junit.RootTestRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
    private ExamService examService;

    /*JSON数据*/
    @Value("classpath:data/klass.json")
    private Resource klassResource;
    @Value("classpath:data/grade.json")
    private Resource gradeResource;
    @Value("classpath:data/subject.json")
    private Resource subjectResource;
    @Value("classpath:data/teacher.json")
    private Resource teacherResource;
    @Value("classpath:data/student.json")
    private Resource studentResource;
    @Value("classpath:data/relevance.json")
    private Resource relevanceResource;

    private static List<User> USERS;
    private static List<Teacher> TEACHERS;
    private static List<Grade> GRADES;
    private static List<Subject> SUBJECTS;
    private static List<Student> STUDENTS;
    private static List<Exam> Exams;
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
        try {
            // 班级
            List<Klass> klasses = JsonUtil.read(klassResource, "classes", Klass.class);
            log.info(klasses.size());
            log.info("====================================");
            List<Grade> grades = JsonUtil.read(gradeResource, "grades", Grade.class);
            log.info(grades.size());
            log.info("====================================");
            // 科目
            List<Subject> subjects = JsonUtil.read(subjectResource, "subjects", Subject.class);
            //log.info(JSON.toJSONString(subjects, true));
            log.info(subjects.size());
            log.info("====================================");
            // 老师
            List<Teacher> teachers = JsonUtil.read(teacherResource, "teachers", Teacher.class);
            //log.info(JSON.toJSONString(teachers, true));
            log.info(teachers.size());
            log.info("====================================");
            // 关联信息
            List<Relevance> relevances = JsonUtil.read(relevanceResource, "relevances", Relevance.class);
            log.info(relevances.size());
            log.info("====================================");
            // 学生
            List<Student> students = JsonUtil.read(studentResource, "students", Student.class);
            log.info(students.size());
            log.info("====================================");
            // 考试成绩
            List<Exam> exams = new ArrayList<>();
            // 2011年
            exams.addAll(JsonUtil.read("data/exam-2011-c1-1.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2011-c1-2.json", "exams", Exam.class));
            // 2012年
            exams.addAll(JsonUtil.read("data/exam-2012-c1-1.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2011-c2-1.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2012-c1-2.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2011-c2-2.json", "exams", Exam.class));
            // 2013年
            exams.addAll(JsonUtil.read("data/exam-2013-c1-1.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2012-c2-1.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2011-c3-1.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2013-c1-2.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2012-c2-2.json", "exams", Exam.class));
            exams.addAll(JsonUtil.read("data/exam-2011-c3-2.json", "exams", Exam.class));
            log.info(exams.size());
            // 保存
            int result = userService.save(grades, klasses, subjects, teachers, relevances, students, exams);
            log.info("受影响行数: {}", result);
        } catch (Exception e) {
            log.error("数据初始化失败: ", e);
            e.printStackTrace();
        }
    }
}
