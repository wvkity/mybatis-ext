package com.wkit.lost.mybatis.starter.example.junit.service;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.plugins.zconfig.ThreadLocalLimit;
import com.wkit.lost.mybatis.plugins.zconfig.ThreadLocalPageable;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import com.wkit.lost.paging.Pager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class PageableQueryApp extends RootTestRunner {

    @Inject
    private SysUserService sysUserService;

    @Test
    public void test() {
        // 分页查询
        Criteria<SysUser> ct1 = sysUserService.getCriteria();
        List<SysUser> list1 = sysUserService.pageableList( new Pager( 2, 0 ), ct1 );
        // 直接指定范围查询
        Criteria<SysUser> ct2 = sysUserService.getCriteria();
        ct2.limit( 60, 80 );
        List<SysUser> list2 = sysUserService.rangeList( ct2 );
        // 根据开始页、结束页、每页数目计算范围查询
        Criteria<SysUser> ct3 = sysUserService.getCriteria();
        ct3.limit( 2, 4, 20 );
        List<SysUser> list3 = sysUserService.rangeList( ct3 );
        // 查询结束后，当前线程下的Limit、Pageable对象是否及时清除
        log.info( ThreadLocalLimit.getLimit() );
        log.info( ThreadLocalPageable.getPageable() );
    }
}
