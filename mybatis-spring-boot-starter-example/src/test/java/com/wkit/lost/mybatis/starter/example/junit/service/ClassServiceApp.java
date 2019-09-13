package com.wkit.lost.mybatis.starter.example.junit.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.core.ForeignCriteria;
import com.wkit.lost.mybatis.starter.example.beans.Class;
import com.wkit.lost.mybatis.starter.example.beans.Teacher;
import com.wkit.lost.mybatis.starter.example.service.ClassService;
import com.wkit.lost.mybatis.starter.example.service.TeacherService;
import com.wkit.lost.mybatis.starter.example.vo.ClassVo;
import com.wkit.lost.mybatis.starter.example.vo.TeacherVo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class ClassServiceApp extends RootTestRunner {

    @Inject
    private ClassService classService;

    @Inject
    private TeacherService teacherService;

    @Test
    public void test() {
        try {
            CriteriaImpl<Class> criteria = classService.getCriteria();
            criteria.in( Class::getId, 1L, 2L ).exclude( "teacherId" );
            ForeignCriteria<Teacher> subCriteria = criteria.createForeign( Teacher.class, "th", "teacher", "teacherId", "id" ).setRelation( true );
            criteria.addForeign( subCriteria );
            // 分组
            //criteria.setGroupAll( true );
            criteria.group( "id", "name" );
            criteria.aliasGroup( "th", "id", "name" );
            List<ClassVo> result = classService.list( criteria );
            log.info( "查询结果: {}", JSON.toJSONString( result, true ) );
            log.info( "master对象：{}", JSON.toJSONString( criteria.getRootMaster(), SerializerFeature.PrettyFormat ) );
        } catch ( Exception e ) {
            log.error( "系统异常了：", e );
        }
    }

    @Test
    public void teacherTest() {
        try {
            CriteriaImpl<Teacher> criteria = teacherService.getCriteria();
            criteria.idEq( 1L );
            ForeignCriteria<Class> subCriteria = criteria.createForeign( Class.class, "cl", "id", "teacherId" );
            //subCriteria.setRelation( true );
            criteria.addForeign( subCriteria );
            List<TeacherVo> result = teacherService.list( criteria );
            log.info( "查询结果: {}", JSON.toJSONString( result, true ) );
        } catch ( Exception e ) {
            log.error( "系统出现异常：", e );
        }
    }

    @Test
    public void tt() {
        System.out.println( System.getProperty( "os.name" ) );
    }
}
