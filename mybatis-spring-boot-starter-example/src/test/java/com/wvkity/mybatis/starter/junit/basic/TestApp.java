package com.wvkity.mybatis.starter.junit.basic;

import com.alibaba.fastjson.JSON;
import com.wvkity.mybatis.starter.example.entity.Exam;
import com.wvkity.mybatis.starter.example.entity.Relevance;
import com.wvkity.mybatis.starter.example.entity.Student;
import com.wvkity.mybatis.starter.example.entity.Teacher;
import com.wvkity.mybatis.starter.example.utils.AddressUtil;
import com.wvkity.mybatis.starter.example.utils.RandomUtil;
import com.wvkity.mybatis.starter.example.utils.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public class TestApp {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final String DEFAULT_USER_NAME = "system";
    private static final LocalDateTime DEFAULT_TIME;
    private static final String[] TEACHER_REMARK = {"语文老师", "数学老师", "英语老师", "历史老师", "物理老师", "化学老师", "体育老师"};
    /**
     * 2000年开始(时间戳)
     */
    private static final int START_TIME = 1970;
    private static final int YEAR_SECONDS = 31449600;
    // 8月中旬-下旬
    private static final int AUG_START = 20304000;
    private static final int AUG_END = 20966000;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        DEFAULT_TIME = LocalDateTime.ofEpochSecond(1219551764L, 0, ZoneOffset.UTC);
    }

    public enum Grades {
        C1, C2, C3
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Generator {
        private boolean first;
        private long start;
        private long end;
        private int year;
        private int period;
        private Grades grade;
        private String firstTime;
        private String secondTime;
        private String thirdTime;
        private String fourTime;
        private String fiveTime;
        private String filePath;
    }

    @Test
    public void randomTest() {
        System.out.println(RandomUtil.nextInt(10, 55));
        for (int i = 0; i < 10; i++) {
            System.out.println(UserUtil.generate(14, 17));
        }
    }

    // 老师信息
    @Test
    public void generateTeacher() {
        int size = 43;
        int min = 30;
        int max = 50;
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            UserUtil.UserInfo ui = UserUtil.generate(min, max);
            Teacher it = new Teacher();
            it.setId(Long.parseLong(i + "")).setName(ui.userName()).setSex(ui.sex())
                    .setPhone(ui.phone()).setRemark(TEACHER_REMARK[remarkIndex(i)]).setDeleted(false)
                    .setCreatedUserId(DEFAULT_USER_ID).setCreatedUserName(DEFAULT_USER_NAME).setGmtCreated(DEFAULT_TIME);
            teachers.add(it);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("teachers", teachers);
        System.out.println(JSON.toJSONString(result, true));
    }

    private int remarkIndex(int i) {
        if (i <= 8) {
            return 0;
        } else if (i <= 16) {
            return 1;
        } else if (i <= 24) {
            return 2;
        } else if (i <= 32) {
            return 3;
        } else if (i <= 38) {
            return 4;
        } else if (i <= 41) {
            return 5;
        } else {
            return 6;
        }
    }

    public static LocalDateTime getBirthday(int start, int age, int offset) {
        int year = start - START_TIME - age;
        LocalDateTime time = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
        return time.plus(Duration.ofDays((year + offset) * 365)).plus(Duration.ofSeconds(RandomUtil.nextInt(YEAR_SECONDS)));
    }

    @Test
    public void addressTest() {
        System.out.println(AddressUtil.getRoad());
        System.out.println(UserUtil.getEmail(7, 15));
        System.out.println(UserUtil.getTel());
        System.out.println(getBirthday(2011, 16, 2));
    }

    // 学生信息
    @Test
    public void generateStudent() {
        List<Student> students = new CopyOnWriteArrayList<>();
        // 2011-1班(初三)
        int size = 45;
        students.addAll(generateStudent(0, 2011, 2, 1L, size, 14, 17));
        // 2011-2班(初三)
        students.addAll(generateStudent(students.size(), 2011, 2, 2L, size, 14, 17));
        // 2011-3班(初三)
        students.addAll(generateStudent(students.size(), 2011, 2, 3L, size, 14, 17));
        // 2011-4班(初三)
        students.addAll(generateStudent(students.size(), 2011, 2, 4L, size, 14, 17));
        // 2012-1班(初二)
        students.addAll(generateStudent(students.size(), 2012, 1, 5L, size, 13, 15));
        // 2012-2班(初二)
        students.addAll(generateStudent(students.size(), 2012, 1, 6L, size, 13, 15));
        // 2012-3班(初二)
        students.addAll(generateStudent(students.size(), 2012, 1, 7L, size, 13, 15));
        // 2012-4班(初二)
        students.addAll(generateStudent(students.size(), 2012, 1, 8L, size, 13, 15));
        // 2013-1班(初一)
        students.addAll(generateStudent(students.size(), 2013, 0, 9L, size, 12, 14));
        // 2013-2班(初一)
        students.addAll(generateStudent(students.size(), 2013, 0, 10L, size, 12, 14));
        // 2013-3班(初一)
        students.addAll(generateStudent(students.size(), 2013, 0, 11L, size, 12, 14));
        // 2013-4班(初一)
        students.addAll(generateStudent(students.size(), 2013, 0, 12L, size, 12, 14));
        Map<String, Object> result = new HashMap<>();
        result.put("students", students);
        try {
            IOUtils.write(JSON.toJSONString(result, true),
                    new FileOutputStream(new File("student.json")), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 学生信息
    public List<Student> generateStudent(long index, int timeStart, int offset, Long klassId, int size, int minAge, int maxAge) {
        List<Student> students = new ArrayList<>(size);
        LocalDateTime time = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
        LocalDateTime gmtCreated = time.plus(Duration.ofDays((timeStart - START_TIME) * 365))
                .plus(Duration.ofSeconds(RandomUtil.nextInt(AUG_START, AUG_END)));
        for (long i = 1; i <= size; i++) {
            UserUtil.UserInfo ui = UserUtil.generate(minAge, maxAge);
            Student it = new Student();
            it.setId(i + index).setName(ui.userName()).setPhone(ui.phone()).setEmail(ui.email()).setSex(ui.sex());
            it.setAddress(ui.address()).setKlassId(klassId).setBirthday(getBirthday(timeStart, ui.age(), offset));
            it.setDeleted(false).setCreatedUserId(DEFAULT_USER_ID).setCreatedUserName(DEFAULT_USER_NAME);
            it.setPeriod(timeStart).setVersion(0).setGmtCreated(gmtCreated);
            students.add(it);
        }
        return students;
    }

    // 考试信息
    @Test
    public void generateExam() {
        /* 1-180(初三年级)
         181-360(初二年级)
         361-540(初一年级)*/
        try {
            // 2011年考试
            generateExam2011();
            // 2012年考试
            generateExam2012();
            // 2013年考试
            generateExam2013();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateExam2011() {
        // 2011年考试
        // 初一第一学期
        Generator.GeneratorBuilder builder = Generator.builder().start(1).end(180).firstTime("2011-10-10 01:00:00");
        builder.secondTime("2011-11-04 01:00:00").thirdTime("2011-11-20 01:00:00").fourTime("2011-12-18 01:00:00");
        builder.first(true).grade(Grades.C1).fiveTime("2012-01-24 01:00:00").filePath("exam-2011-c1-1.json");
        builder.period(2011).year(2011);
        generateExam(builder.build());
        // 初一第二学期
        builder.firstTime("2012-03-08 01:00:00").secondTime("2012-04-13 01:00:00").thirdTime("2012-05-10 01:00:00");
        builder.fourTime("2012-06-02 01:00:00").fiveTime("2012-06-24 01:00:00");
        builder.first(false).year(2012).filePath("exam-2011-c1-2.json");
        generateExam(builder.build());
    }

    public void generateExam2012() {
        // 2012年考试
        // 第一学期
        // 初一
        Generator.GeneratorBuilder builder = Generator.builder().start(181).end(360).firstTime("2012-10-08 01:00:00");
        builder.secondTime("2011-11-01 01:00:00").thirdTime("2011-12-02 01:00:00").fourTime("2011-12-28 01:00:00");
        builder.first(true).grade(Grades.C1).fiveTime("2012-01-25 01:00:00").filePath("exam-2012-c1-1.json");
        builder.period(2012).year(2012);
        generateExam(builder.build());
        // 初二
        builder.start(1).end(180).period(2011).grade(Grades.C2).filePath("exam-2011-c2-1.json");
        generateExam(builder.build());
        // 第二学期
        // 初一
        builder.firstTime("2013-03-06 01:00:00").secondTime("2013-04-11 01:00:00").thirdTime("2013-05-08 01:00:00");
        builder.fourTime("2013-06-04 01:00:00").fiveTime("2013-06-25 01:00:00");
        builder.period(2012).grade(Grades.C1).first(false).year(2013).filePath("exam-2012-c1-2.json");
        builder.start(181).end(360);
        generateExam(builder.build());
        // 初二
        builder.start(1).end(180);
        builder.period(2011).grade(Grades.C2).filePath("exam-2011-c2-2.json");
        generateExam(builder.build());
    }

    public void generateExam2013() {
        // 2013年考试
        // 第一学期
        // 初一
        Generator.GeneratorBuilder builder = Generator.builder().start(361).end(540).firstTime("2013-10-07 01:00:00");
        builder.secondTime("2013-11-02 01:00:00").thirdTime("2013-12-04 01:00:00").fourTime("2013-12-26 01:00:00");
        builder.first(true).grade(Grades.C1).fiveTime("2014-01-22 01:00:00").filePath("exam-2013-c1-1.json");
        builder.period(2013).year(2013);
        generateExam(builder.build());
        // 初二
        builder.period(2012).grade(Grades.C2).filePath("exam-2012-c2-1.json");
        builder.start(181).end(360);
        generateExam(builder.build());
        // 初三
        builder.period(2011).grade(Grades.C3).filePath("exam-2011-c3-1.json");
        builder.start(1).end(180);
        generateExam(builder.build());
        // 第二学期
        // 初一
        builder.firstTime("2014-03-10 01:00:00").secondTime("2014-04-07 01:00:00").thirdTime("2014-05-10 01:00:00");
        builder.start(361).end(540).fourTime("2014-06-06 01:00:00").fiveTime("2014-06-27 01:00:00");
        builder.period(2013).grade(Grades.C1).first(false).year(2014).filePath("exam-2013-c1-2.json");
        generateExam(builder.build());
        // 初二
        builder.period(2012).grade(Grades.C2).filePath("exam-2012-c2-2.json");
        builder.start(181).end(360);
        generateExam(builder.build());
        // 初三
        builder.period(2011).grade(Grades.C3).fiveTime("2014-06-18 01:00:00").filePath("exam-2011-c3-2.json");
        builder.start(1).end(180);
        generateExam(builder.build());
    }

    public void generateExam(Generator it) {
        /* 1-180(初三年级)
         181-360(初二年级)
         361-540(初一年级)*/
        Map<String, Object> result = new HashMap<>();
        List<Exam> exams = new CopyOnWriteArrayList<>();
        int semester = it.isFirst() ? 1 : 2;
        int year = it.getYear();
        int period = it.getPeriod();
        long start = it.getStart();
        long end = it.getEnd();
        Grades grade = it.getGrade();
        String gc = grade == Grades.C1 ? "初一年级" : (grade == Grades.C2 ? "初二年级" : "初三年级");
        int gi = grade == Grades.C1 ? 1 : (grade == Grades.C2 ? 2 : 3);
        String code = gc + (it.isFirst() ? "第一学期" : "第二学期");
        int yw = (grade == Grades.C1 ? 1 : grade == Grades.C2 ? 5 : 10);
        int sx = (grade == Grades.C1 ? 2 : grade == Grades.C2 ? 6 : 11);
        int yy = (grade == Grades.C1 ? 3 : grade == Grades.C2 ? 7 : 12);
        int ls = (grade == Grades.C1 ? 4 : grade == Grades.C2 ? 8 : 13);
        String c1 = year + "年" + code + "综合测试(一)";
        LocalDateTime t1 = LocalDateTime.from(FORMATTER.parse(it.getFirstTime()));
        LocalDateTime t2 = t1.plus(Duration.ofHours(5));
        LocalDateTime ct1 = t2.plus(Duration.ofDays(RandomUtil.nextInt(3, 5))).plus(Duration.ofMinutes(RandomUtil.nextInt(180, 420)));
        // 语文
        exams.addAll(generateExam(gi, semester, year, period, start, end, yw, c1, 33, 120, t1, ct1));
        // 数学
        exams.addAll(generateExam(gi, semester, year, period, start, end, sx, c1, 28, 120, t2, ct1));
        // 英语
        exams.addAll(generateExam(gi, semester, year, period, start, end, yy, c1, 35, 120, t1.plus(Duration.ofDays(1)), ct1));
        // 历史
        exams.addAll(generateExam(gi, semester, year, period, start, end, ls, c1, 40, 100, t2.plus(Duration.ofDays(1)), ct1));
        if (grade == Grades.C2) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 9, c1, 35, 100, t1.plus(Duration.ofDays(2)), ct1));
        } else if (grade == Grades.C3) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 14, c1, 35, 100, t1.plus(Duration.ofDays(2)), ct1));
            // 化学
            exams.addAll(generateExam(gi, semester, year, period, start, end, 15, c1, 40, 100, t2.plus(Duration.ofDays(2)), ct1));
        }
        String c2 = year + "年" + code + "综合测试(二)";
        LocalDateTime t3 = LocalDateTime.from(FORMATTER.parse(it.getSecondTime()));
        LocalDateTime t4 = t3.plus(Duration.ofHours(5));
        LocalDateTime ct2 = t4.plus(Duration.ofDays(RandomUtil.nextInt(3, 5))).plus(Duration.ofMinutes(RandomUtil.nextInt(180, 420)));
        // 语文
        exams.addAll(generateExam(gi, semester, year, period, start, end, yw, c2, 42, 120, t3, ct2));
        // 数学
        exams.addAll(generateExam(gi, semester, year, period, start, end, sx, c2, 34, 120, t4, ct2));
        // 英语
        exams.addAll(generateExam(gi, semester, year, period, start, end, yy, c2, 38, 120, t3.plus(Duration.ofDays(1)), ct2));
        // 历史
        exams.addAll(generateExam(gi, semester, year, period, start, end, ls, c2, 34, 100, t4.plus(Duration.ofDays(1)), ct2));
        if (grade == Grades.C2) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 9, c2, 35, 100, t3.plus(Duration.ofDays(2)), ct2));
        } else if (grade == Grades.C3) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 14, c2, 35, 100, t3.plus(Duration.ofDays(2)), ct2));
            // 化学
            exams.addAll(generateExam(gi, semester, year, period, start, end, 15, c2, 40, 100, t4.plus(Duration.ofDays(2)), ct2));
        }
        String c3 = year + "年" + code + "期中考试";
        LocalDateTime t5 = LocalDateTime.from(FORMATTER.parse(it.getThirdTime()));
        LocalDateTime t6 = t5.plus(Duration.ofHours(5));
        LocalDateTime ct3 = t6.plus(Duration.ofDays(RandomUtil.nextInt(3, 5))).plus(Duration.ofMinutes(RandomUtil.nextInt(180, 420)));
        // 语文
        exams.addAll(generateExam(gi, semester, year, period, start, end, yw, c3, 38, 120, t5, ct3));
        // 数学
        exams.addAll(generateExam(gi, semester, year, period, start, end, sx, c3, 40, 120, t6, ct3));
        // 英语
        exams.addAll(generateExam(gi, semester, year, period, start, end, yy, c3, 32, 120, t5.plus(Duration.ofDays(1)), ct3));
        // 历史
        exams.addAll(generateExam(gi, semester, year, period, start, end, ls, c3, 28, 100, t6.plus(Duration.ofDays(1)), ct3));
        if (grade == Grades.C2) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 9, c3, 21, 100, t5.plus(Duration.ofDays(2)), ct3));
        } else if (grade == Grades.C3) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 14, c3, 33, 100, t5.plus(Duration.ofDays(2)), ct3));
            // 化学
            exams.addAll(generateExam(gi, semester, year, period, start, end, 15, c3, 40, 100, t6.plus(Duration.ofDays(2)), ct3));
        }
        String c4 = year + "年" + code + "综合测试(三)";
        LocalDateTime t7 = LocalDateTime.from(FORMATTER.parse(it.getFourTime()));
        LocalDateTime t8 = t7.plus(Duration.ofHours(5));
        LocalDateTime ct4 = t8.plus(Duration.ofDays(RandomUtil.nextInt(3, 5))).plus(Duration.ofMinutes(RandomUtil.nextInt(180, 420)));
        // 语文
        exams.addAll(generateExam(gi, semester, year, period, start, end, yw, c4, 42, 120, t7, ct4));
        // 数学
        exams.addAll(generateExam(gi, semester, year, period, start, end, sx, c4, 34, 120, t8, ct4));
        // 英语
        exams.addAll(generateExam(gi, semester, year, period, start, end, yy, c4, 38, 120, t7.plus(Duration.ofDays(1)), ct4));
        // 历史
        exams.addAll(generateExam(gi, semester, year, period, start, end, ls, c4, 34, 100, t8.plus(Duration.ofDays(1)), ct4));
        if (grade == Grades.C2) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 9, c4, 24, 100, t7.plus(Duration.ofDays(2)), ct4));
        } else if (grade == Grades.C3) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 14, c4, 28, 100, t7.plus(Duration.ofDays(2)), ct4));
            // 化学
            exams.addAll(generateExam(gi, semester, year, period, start, end, 15, c4, 31, 100, t8.plus(Duration.ofDays(2)), ct4));
        }
        String c5 = year + "年" + code + "期末考试";
        LocalDateTime t9 = LocalDateTime.from(FORMATTER.parse(it.getFiveTime()));
        LocalDateTime t10 = t9.plus(Duration.ofHours(5));
        LocalDateTime ct5 = t10.plus(Duration.ofDays(RandomUtil.nextInt(3, 5))).plus(Duration.ofMinutes(RandomUtil.nextInt(180, 420)));
        // 语文
        exams.addAll(generateExam(gi, semester, year, period, start, end, yw, c5, 38, 120, t9, ct5));
        // 数学
        exams.addAll(generateExam(gi, semester, year, period, start, end, sx, c5, 40, 120, t10, ct5));
        // 英语
        exams.addAll(generateExam(gi, semester, year, period, start, end, yy, c5, 32, 120, t9.plus(Duration.ofDays(1)), ct5));
        // 历史
        exams.addAll(generateExam(gi, semester, year, period, start, end, ls, c5, 28, 100, t10.plus(Duration.ofDays(1)), ct5));
        if (grade == Grades.C2) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 9, c5, 21, 100, t9.plus(Duration.ofDays(2)), ct5));
        } else if (grade == Grades.C3) {
            // 物理
            exams.addAll(generateExam(gi, semester, year, period, start, end, 14, c5, 32, 100, t9.plus(Duration.ofDays(2)), ct5));
            // 化学
            exams.addAll(generateExam(gi, semester, year, period, start, end, 15, c5, 30, 100, t10.plus(Duration.ofDays(2)), ct5));
        }
        result.put("exams", exams);
        try {
            IOUtils.write(JSON.toJSONString(result, true), new FileOutputStream(new File(it.getFilePath())), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Exam> generateExam(int grade, int semester, int year, int period, long start, long end, long subjectId, String code, int minScore,
                                   int maxScore, LocalDateTime gmtExamDate, LocalDateTime gmtCreated) {
        List<Exam> exams = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            Exam it = new Exam();
            it.setStudentId(i).setScore(RandomUtil.nextInt(minScore, maxScore)).setExamDate(gmtExamDate);
            it.setSubjectId(subjectId).setExamCode(code).setGmtCreated(gmtCreated).setCreatedUserId(DEFAULT_USER_ID);
            it.setExamYear(year).setCreatedUserName(DEFAULT_USER_NAME).setDeleted(false).setPeriod(period).setGrade(grade);
            it.setSemester(semester);
            exams.add(it);
        }
        return exams;
    }

    private List<Long> asList(Long... values) {
        return new ArrayList<>(Arrays.asList(values));
    }

    @Test
    public void generateRelevance() {
        List<Relevance> relevances = new ArrayList<>();
        relevances.addAll(generateRelevance2011());
        relevances.addAll(generateRelevance2012());
        relevances.addAll(generateRelevance2013());
        log.info(relevances.size());
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("relevances", relevances);
            IOUtils.write(JSON.toJSONString(result, true), new FileOutputStream(new File("relevance.json")), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Relevance> generateRelevance2011() {
        List<Relevance> relevances = new ArrayList<>();
        // 第一学期
        // 2011-初一第一学期语文
        relevances.addAll(generateRelevance(2011, 2011, 1, 1, true, asList(1L, 2L), asList(1L, 2L, 3L, 4L)));
        // 2011-初一第一学期数学
        relevances.addAll(generateRelevance(2011, 2011, 1, 2, true, asList(9L, 10L), asList(1L, 2L, 3L, 4L)));
        // 2011-初一第一学期英语
        relevances.addAll(generateRelevance(2011, 2011, 1, 3, true, asList(17L, 18L), asList(1L, 2L, 3L, 4L)));
        // 2011-初一第一学期历史
        relevances.addAll(generateRelevance(2011, 2011, 1, 4, true, asList(25L, 26L), asList(1L, 2L, 3L, 4L)));
        // 第二学期
        // 2011-初一第二学期历史
        relevances.addAll(generateRelevance(2012, 2011, 2, 1, true, asList(1L, 4L), asList(1L, 2L, 3L, 4L)));
        // 2011-初一第二学期历史
        relevances.addAll(generateRelevance(2012, 2011, 2, 2, true, asList(9L, 11L), asList(1L, 2L, 3L, 4L)));
        // 2011-初一第二学期历史
        relevances.addAll(generateRelevance(2012, 2011, 2, 3, true, asList(17L, 20L), asList(1L, 2L, 3L, 4L)));
        // 2011-初一第二学期历史
        relevances.addAll(generateRelevance(2012, 2011, 2, 4, true, asList(25L, 29L), asList(1L, 2L, 3L, 4L)));
        return relevances;
    }

    public List<Relevance> generateRelevance2012() {
        List<Relevance> relevances = new ArrayList<>();
        // 第一学期
        // 初一
        // 2012-第一学期语文
        relevances.addAll(generateRelevance(2012, 2012, 1, 1, true, asList(1L, 2L), asList(5L, 6L, 7L, 8L)));
        // 2012-第一学期数学
        relevances.addAll(generateRelevance(2012, 2012, 1, 2, true, asList(9L, 10L), asList(5L, 6L, 7L, 8L)));
        // 2012-第一学期英语
        relevances.addAll(generateRelevance(2012, 2012, 1, 3, true, asList(17L, 18L), asList(5L, 6L, 7L, 8L)));
        // 2012-第一学期历史
        relevances.addAll(generateRelevance(2012, 2012, 1, 4, true, asList(25L, 26L), asList(5L, 6L, 7L, 8L)));
        // 初二
        // 2011-第一学期语文
        relevances.addAll(generateRelevance(2012, 2011, 1, 5, false, asList(3L, 4L, 5L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期数学
        relevances.addAll(generateRelevance(2012, 2011, 1, 6, false, asList(11L, 12L, 13L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期英语
        relevances.addAll(generateRelevance(2012, 2011, 1, 7, false, asList(19L, 21L, 22L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期历史
        relevances.addAll(generateRelevance(2012, 2011, 1, 8, false, asList(27L, 28L, 30L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期物理
        relevances.addAll(generateRelevance(2012, 2011, 1, 9, false, asList(34L, 36L, 37L), asList(1L, 2L, 3L, 4L)));

        // 第二学期
        // 初一
        // 2012-第二学期语文
        relevances.addAll(generateRelevance(2013, 2012, 2, 1, true, asList(1L, 2L), asList(5L, 6L, 7L, 8L)));
        // 2012-第二学期语文
        relevances.addAll(generateRelevance(2013, 2012, 2, 2, true, asList(9L, 10L), asList(5L, 6L, 7L, 8L)));
        // 2012-第二学期语文
        relevances.addAll(generateRelevance(2013, 2012, 2, 3, true, asList(17L, 18L), asList(5L, 6L, 7L, 8L)));
        // 2012-第二学期语文
        relevances.addAll(generateRelevance(2013, 2012, 2, 4, true, asList(25L, 26L), asList(5L, 6L, 7L, 8L)));
        // 初二
        // 2011-第二学期语文
        relevances.addAll(generateRelevance(2013, 2011, 2, 5, false, asList(3L, 4L, 5L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期语文
        relevances.addAll(generateRelevance(2013, 2011, 2, 6, false, asList(11L, 12L, 13L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期语文
        relevances.addAll(generateRelevance(2013, 2011, 2, 7, false, asList(20L, 21L, 22L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期语文
        relevances.addAll(generateRelevance(2013, 2011, 2, 8, false, asList(27L, 28L, 29L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期语文
        relevances.addAll(generateRelevance(2013, 2011, 2, 9, false, asList(35L, 36L, 37L), asList(1L, 2L, 3L, 4L)));
        return relevances;
    }

    public List<Relevance> generateRelevance2013() {
        List<Relevance> relevances = new ArrayList<>();
        // 第一学期
        // 初一
        // 2013-第一学期语文
        relevances.addAll(generateRelevance(2013, 2013, 1, 1, true, asList(7L, 8L), asList(9L, 10L, 11L, 12L)));
        // 2013-第一学期数学
        relevances.addAll(generateRelevance(2013, 2013, 1, 2, true, asList(9L, 13L), asList(9L, 10L, 11L, 12L)));
        // 2013-第一学期英语
        relevances.addAll(generateRelevance(2013, 2013, 1, 3, true, asList(19L, 20L), asList(9L, 10L, 11L, 12L)));
        // 2013-第一学期历史
        relevances.addAll(generateRelevance(2013, 2013, 1, 4, true, asList(27L, 28L), asList(9L, 10L, 11L, 12L)));
        // 初二
        // 2012-第一学期语文
        relevances.addAll(generateRelevance(2013, 2012, 1, 5, false, asList(1L, 2L, 3L), asList(5L, 6L, 7L, 8L)));
        // 2012-第一学期数学
        relevances.addAll(generateRelevance(2013, 2012, 1, 6, false, asList(10L, 11L, 12L), asList(5L, 6L, 7L, 8L)));
        // 2012-第一学期英语
        relevances.addAll(generateRelevance(2013, 2012, 1, 7, false, asList(18L, 21L, 22L), asList(5L, 6L, 7L, 8L)));
        // 2012-第一学期历史
        relevances.addAll(generateRelevance(2013, 2012, 1, 8, false, asList(25L, 29L, 30L), asList(5L, 6L, 7L, 8L)));
        // 2012-第一学期物理
        relevances.addAll(generateRelevance(2013, 2012, 1, 9, false, asList(35L, 36L, 37L), asList(5L, 6L, 7L, 8L)));
        // 初三
        // 2011-第一学期语文
        relevances.addAll(generateRelevance(2013, 2011, 1, 10, false, asList(4L, 5L, 6L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期数学
        relevances.addAll(generateRelevance(2013, 2011, 1, 11, false, asList(14L, 15L, 16L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期英语
        relevances.addAll(generateRelevance(2013, 2011, 1, 12, false, asList(17L, 23L, 24L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期历史
        relevances.addAll(generateRelevance(2013, 2011, 1, 13, false, asList(26L, 31L, 32L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学期物理
        relevances.addAll(generateRelevance(2013, 2011, 1, 14, false, asList(33L, 34L, 38L), asList(1L, 2L, 3L, 4L)));
        // 2011-第一学区化学
        relevances.addAll(generateRelevance(2013, 2011, 1, 15, false, asList(39L, 40L, 41L), asList(1L, 2L, 3L, 4L)));

        // 第二学期
        // 初一
        // 2013-第二学期语文
        relevances.addAll(generateRelevance(2013, 2013, 2, 1, true, asList(7L, 8L), asList(9L, 10L, 11L, 12L)));
        // 2013-第二学期数学
        relevances.addAll(generateRelevance(2013, 2013, 2, 2, true, asList(9L, 13L), asList(9L, 10L, 11L, 12L)));
        // 2013-第二学期英语
        relevances.addAll(generateRelevance(2013, 2013, 2, 3, true, asList(19L, 20L), asList(9L, 10L, 11L, 12L)));
        // 2013-第二学期历史
        relevances.addAll(generateRelevance(2013, 2013, 2, 4, true, asList(27L, 28L), asList(9L, 10L, 11L, 12L)));
        // 初二
        // 2012-第二学期语文
        relevances.addAll(generateRelevance(2013, 2012, 2, 5, false, asList(1L, 2L, 3L), asList(5L, 6L, 7L, 8L)));
        // 2012-第二学期数学
        relevances.addAll(generateRelevance(2013, 2012, 2, 6, false, asList(10L, 11L, 12L), asList(5L, 6L, 7L, 8L)));
        // 2012-第二学期英语
        relevances.addAll(generateRelevance(2013, 2012, 2, 7, false, asList(18L, 21L, 22L), asList(5L, 6L, 7L, 8L)));
        // 2012-第二学期历史
        relevances.addAll(generateRelevance(2013, 2012, 2, 8, false, asList(25L, 29L, 30L), asList(5L, 6L, 7L, 8L)));
        // 2012-第二学期物理
        relevances.addAll(generateRelevance(2013, 2012, 2, 9, false, asList(35L, 36L, 37L), asList(5L, 6L, 7L, 8L)));
        // 初三
        // 2011-第二学期语文
        relevances.addAll(generateRelevance(2013, 2011, 2, 10, false, asList(4L, 5L, 6L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期数学
        relevances.addAll(generateRelevance(2013, 2011, 2, 11, false, asList(14L, 15L, 16L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期英语
        relevances.addAll(generateRelevance(2013, 2011, 2, 12, false, asList(17L, 23L, 24L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期历史
        relevances.addAll(generateRelevance(2013, 2011, 2, 13, false, asList(26L, 31L, 32L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学期物理
        relevances.addAll(generateRelevance(2013, 2011, 2, 14, false, asList(33L, 34L, 38L), asList(1L, 2L, 3L, 4L)));
        // 2011-第二学区化学
        relevances.addAll(generateRelevance(2013, 2011, 2, 15, false, asList(39L, 40L, 41L), asList(1L, 2L, 3L, 4L)));
        return relevances;
    }

    public List<Relevance> generateRelevance(int year, int period, int semester, long subject, boolean isDisDiv,
                                             List<Long> teachers, List<Long> klasses) {
        List<Relevance> relevances = new ArrayList<>();
        List<Long> newKlasses = new CopyOnWriteArrayList<>(klasses);
        if (isDisDiv) {
            for (Long teacher : teachers) {
                // 随机获取两个班级
                relevances.add(generate(teacher, newKlasses).setYear(year)
                        .setPeriod(period).setSemester(semester).setSubjectId(subject));
                relevances.add(generate(teacher, newKlasses).setYear(year)
                        .setPeriod(period).setSemester(semester).setSubjectId(subject));
            }
        } else {
            boolean isRandom = true;
            int size = teachers.size() - 1;
            for (int i = 0; i <= size; i++) {
                Long teacher = teachers.get(i);
                if (isRandom) {
                    // 随机获取是否带两个班级
                    boolean temp = RandomUtil.nextInt(5) % 2 == 0;
                    if (temp || i == size) {
                        isRandom = false;
                        relevances.add(generate(teacher, newKlasses).setYear(year)
                                .setPeriod(period).setSemester(semester).setSubjectId(subject));
                    }
                }
                relevances.add(generate(teacher, newKlasses).setYear(year)
                        .setPeriod(period).setSemester(semester).setSubjectId(subject));
            }
        }
        return relevances;
    }

    public Relevance generate(Long teacher, List<Long> klasses) {
        int size = klasses.size() - 1;
        if (size == 0) {
            return new Relevance(teacher, klasses.get(0));
        } else {
            int index = RandomUtil.nextInt(size);
            Long k1 = klasses.get(index);
            klasses.remove(index);
            return new Relevance(teacher, k1);
        }
    }
}
