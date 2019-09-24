package com.wkit.lost.mybatis.starter.example.junit.service;

import com.alibaba.fastjson.JSON;
import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.service.SchoolService;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import com.wkit.lost.paging.Pager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@Log4j2
public class CriteriaServiceApp extends RootTestRunner {

    @Inject
    private SysUserService sysUserService;

    @Inject
    private SchoolService schoolService;

    @Test
    public void existsByCriteria() {
        try {
            //var criteria = new CriteriaImpl<>( SysUser.class );
            CriteriaImpl<SysUser> criteria = sysUserService.getCriteria();
            //criteria.or( ctx -> ctx.idEq( 8L ).or().idEq( 9L ) );
            //criteria.idEq( 8L ).or().idEq( 4L );
            //criteria.add( criteria.idEqual( 5L ).orIdEqual( 6L ) );
            //criteria.condition( cm -> cm.idEqual( 7L ).orIdEqual( 9L ).orEqual( SysUser::getId, 8L ) );
            // 嵌套例子1：有点复杂，需要Restrictions条件工具辅助类
            //criteria.idEq( 5L ).or().idEq( 6L ).nested( criteria, Restrictions.idEqual( 7L ), Restrictions.equal( "state", 5 ) );
            // 嵌套例子2：依赖AbstractConditionManager.flush()方法
            /*criteria.idEq( 5L )
                    .or()
                    .idEq( 6L )
                    .nesting( cm -> cm.idEqual( 9L ).equal( SysUser::getState, 8 ) )
                    .flush()
                    .end()
                    .orNesting( cm -> cm.idEqual( 101L ).equal( SysUser::getState, 6 ) )
                    .flush();*/
            // 嵌套例子3：
            /*criteria.useAlias( "tt" )
                    .idEq( 6L )
                    .orIdEq( 10L )
                    .or( ctx -> ctx.idEq( 8L ).eq( SysUser::getState, 4 ).nesting( cm -> cm.idEqual( 9L ).orEqual( SysUser::getState, 5 ) ).flush() );*/
            /*criteria.useAlias( "ff" )
                    .ne( SysUser::getState, 4 )
                    .lt( SysUser::getState, 1 );*/
            /*criteria.useAlias( "tf" )
                    .notEqual( SysUser::getState, 4 )
                    .lessThan( SysUser::getState, 1 )
                    .flush();*/
            /*criteria.useAlias( "gg" )
                    .lt( SysUser::getState, 6 )
                    .ltEq( SysUser::getId, 8L )
                    .flush();*/
            //criteria.useAlias( "gg" ).le( "state", 6 );
            /*criteria.useAlias( "gg" )
                    .le( SysUser::getState, 7 )
                    .orGe( SysUser::getId, 1000000 );*/
            /*criteria.useAlias( "ft" )
                    .idEq( 5L )
                    .or()
                    .nesting( cm -> cm.idEqExp( 6L ).eqExp( SysUser::getState, 6 ) )
                    .flush()
                    .end()
                    .orGeExp( SysUser::getState, 11 )
                    .flush();*/
            /*criteria.useAlias( "t1" )
                    .isNull( SysUser::getGmtCreate )
                    .orIsNull( SysUser::getCreateUser )
                    .orIsNullExp( SysUser::getId )
                    .orIsNullExp( SysUser::getState )
                    .flush();*/
            /*criteria.useAlias( "t2" )
                    .notNull( SysUser::getGmtModify )
                    .or()
                    .notNull( SysUser::getCreateUser )
                    .end()
                    .orNotNullExp( SysUser::getState )
                    .flush();*/
            /*criteria.useAlias( "t3" )
                    .like( SysUser::getUserName, "张" )
                    .orLikeLeft( SysUser::getPassword, "555" )
                    .orLikeRight( SysUser::getPassword, "8578a" )
                    .orLikeExp( SysUser::getUserName, "李" )
                    .orLikeLeftExp( SysUser::getPassword, "777" )
                    .orLikeRightExp( SysUser::getModifyUser, "张" )
                    .flush()
                    .end();*/
            /*criteria.useAlias( "t4" )
                    .in( SysUser::getId, 4L, 5L, 6L )
                    .or()
                    .in( SysUser::getState, 11, 13 )
                    .reset()
                    .orIn( SysUser::getPassword, "123", "456" )
                    .orInExp( SysUser::getUserName, "张三", "李四" )
                    .orNotInExp( SysUser::getState, 8, 9 )
                    .flush()
                    .orNotIn( SysUser::getId, 6L, 11L );*/
            /*criteria.useAlias( "t5" )
                    .between( SysUser::getState, 11, 15 )
                    .orBetween( SysUser::getState, 25, 28 )
                    .orBetweenExp( SysUser::getState, 44, 48 )
                    .flush()
                    .or()
                    .between( SysUser::getId, 7L, 10L )
                    .reset();*/
            /*criteria.useAlias( "t6" )
                    .notBetween( SysUser::getState, 1, 11 )
                    .orNotBetween( SysUser::getId, 1L, 100000 )
                    .orNotBetweenExp( SysUser::getState, 22, 30 )
                    .flush()
                    .orBetween( SysUser::getPassword, "123", "456" );*/
            /*criteria.useAlias( "t7" )
                    //.queries( SysUser::getId, SysUser::getState, SysUser::getPassword, SysUser::getUserName )
                    //.enableAlias( false )
                    .exclude( "gmtCreate", "gmtModify", "createUser" )
                    .template( "LENGTH({}) > {}", SysUser::getUserName, 10 )
                    .orTemplate( "LEFT({}, 3) = {}", SysUser::getPassword, "123" )
                    .orTemplate( "RIGHT({}, 1) = {}", SysUser::getUserName, "另"  )
                    .exactTemplate( "OR LEFT({}, 1) = {}", SysUser::getCreateUser, "神" );*/
            /*Pageable pager = new Pager();
            List<SysUser> result = sysUserService.pageableList( pager, criteria );
            log.info( "分页信息：{}", JSON.toJSONString( pager, true ) );
            log.info( "执行结果：{}", JSON.toJSONString( result, true ) );*/
            /*criteria.useAlias( "t8" )
                    .idEq( 8L )
                    .or( ctx -> ctx.idEq( 22L ).eq( SysUser::getState, 9 ) )
                    .orNesting( cm -> cm.idEqExp( 33L ).likeRightExp( SysUser::getPassword, "123" ) )
                    .flush();*/
            //criteria.nativeSql( " AND USER_NAME = '张三'" ).template( "{} >= {}", SysUser::getState, 4 );
            /*criteria.nativeSql( " AND USER_NAME = '张6三'" )
                    .orTemplate( "{} >= {}", SysUser::getState, 20 )
                    .or()
                    .template( "{} IN ({})", SysUser::getId, 7L, 8L, 9L, 10L )
                    .and()
                    .orIdEq( 8L )
                    .exactTemplate( "OR {} = {}", SysUser::getCreateUser, "李思" );*/
            criteria.ge( SysUser::getState, 6 );
            criteria.enableAlias( true ).asc( "state", "password" ).desc( "id" ).asc( "createUser" );
            boolean result = sysUserService.exists( criteria );
            log.info( "执行结果：{}", result );
            log.info( "执行结果：{}", JSON.toJSONString( sysUserService.list( new Pager( 1L, 40L ), criteria ) ) );
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error( "发生异常：{}", e.getMessage(), e );
        }
    }

    @Test
    public void getCriteriaFromCache() {
        try {
            CriteriaImpl<SysUser> criteria = this.sysUserService.getCriteria();
            log.info( "{}", criteria );
            //criteria.like( SysUser::getUserName, "羊" ).useAlias( "ts" );
            boolean result = this.sysUserService.exists( criteria );
            log.info( "执行结果：{}", result );
            log.info( "{}", this.sysUserService.exists( this.sysUserService.getCriteria().idEq( 6L ).useAlias( "ff" ) ) );
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error( "发生异常：{}", e.getMessage(), e );
        }
    }

    public void template() {
        try {
            int result = 1;
            log.info( "执行结果：{}", result );
        } catch ( Exception e ) {
            log.error( "发生异常：{}", e.getMessage(), e );
        }
    }
}
