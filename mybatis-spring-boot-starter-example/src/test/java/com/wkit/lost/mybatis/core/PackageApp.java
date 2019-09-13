package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.starter.example.beans.Teacher;
import com.wkit.lost.mybatis.starter.example.service.TeacherService;

import javax.inject.Inject;

public class PackageApp {

    @Inject
    private TeacherService teacherService;

    public void test() {
        CriteriaImpl<Teacher> criteria = teacherService.getCriteria();
        //AtomicInteger atomicInteger = criteria.getParameterSequence();
    }
}
