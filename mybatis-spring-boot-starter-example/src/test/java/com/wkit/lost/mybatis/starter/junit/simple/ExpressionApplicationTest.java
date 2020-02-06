package com.wkit.lost.mybatis.starter.junit.simple;

import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;
import com.wkit.lost.mybatis.core.criteria.Logic;
import com.wkit.lost.mybatis.core.criteria.MatchMode;
import com.wkit.lost.mybatis.core.condition.criterion.Restrictions;
import com.wkit.lost.mybatis.core.condition.expression.Equal;
import com.wkit.lost.mybatis.core.condition.expression.Like;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import com.wkit.lost.mybatis.starter.junit.RootTestRunner;
import com.wkit.lost.paging.Pager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class ExpressionApplicationTest extends RootTestRunner {

    @Inject
    UserService userService;

    @Test
    public void equalTest() {
        CriteriaImpl<User> criteria = userService.getCriteria();
        Equal<User> equal1 = Restrictions.eq( criteria, "id", 1L );
        Equal<User> equal2 = Restrictions.eq( criteria, "id", 1L );
        Equal<User> equal3 = Restrictions.eq( criteria, "id", 5L, Logic.OR );
        Equal<User> equal4 = Restrictions.eq( criteria, "id", 6L, Logic.OR );
        Like<User> like1 = Restrictions.like( criteria, "userName", "松", MatchMode.START, Logic.OR );
        criteria.add( equal1, equal2, equal3, equal4, like1 ).exclude( "gmtDelete", "deleteUser" );
        log.info( "Equal expression info: {}", equal1 );
        log.info( "Equal expression info: {}", equal2 );
        log.info( "Equal expression info: {}", equal3 );
        log.info( "Equal expression info: {}", equal4 );
        log.info( "Equal expression info: {}", like1 );
        log.info( "Equal1 - hashcode: {}", equal1.hashCode() );
        log.info( "Equal2 - hashcode: {}", equal2.hashCode() );
        log.info( "Equal1 == Equal2: {}", equal1.equals( equal2 ) );
        //List<User> result = userService.list( criteria.limit( 8, 16 ).asc( "state" ).desc( "gmtCreate" ) );
        List<User> result = userService.list( new Pager(), criteria.asc( "state" ).desc( "gmtCreate" ) );
        log.info( "结果: {}", result );
    }
}
