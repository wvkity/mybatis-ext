package com.wvkity.mybatis.starter.example.service;

import com.wvkity.mybatis.service.SimpleService;
import com.wvkity.mybatis.starter.example.entity.Exam;
import com.wvkity.mybatis.starter.example.entity.Grade;
import com.wvkity.mybatis.starter.example.entity.Klass;
import com.wvkity.mybatis.starter.example.entity.Relevance;
import com.wvkity.mybatis.starter.example.entity.Student;
import com.wvkity.mybatis.starter.example.entity.Subject;
import com.wvkity.mybatis.starter.example.entity.Teacher;
import com.wvkity.mybatis.starter.example.entity.User;

import java.util.List;

public interface UserService extends SimpleService<User> {

    int testSave(Grade grade, List<User> users);
    
    int save(List<Grade> grades, List<Klass> klasses, List<Subject> subjects, List<Teacher> teachers,
             List<Relevance> relevances, List<Student> students, List<Exam> exams);
}
