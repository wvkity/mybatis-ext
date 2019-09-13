package com.wkit.lost.mybatis.starter.example.controller;

import com.wkit.lost.mybatis.core.CriteriaImpl;
import com.wkit.lost.mybatis.starter.example.beans.SysUser;
import com.wkit.lost.mybatis.starter.example.service.SysUserService;
import com.wkit.lost.paging.Pageable;
import com.wkit.lost.paging.Pager;
import com.wkit.lost.result.core.DataResult;
import com.wkit.lost.result.core.Result;
import com.wkit.lost.result.model.Model;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@Log4j2
@RestController
public class DataTestController {

    @Inject
    private SysUserService sysUserService;

    @SuppressWarnings( "unchecked" )
    @RequestMapping( value = "data" )
    public Model data( @RequestParam( name = "page", required = false ) String page,
                       @RequestParam( name = "size", required = false, defaultValue = "20" ) String size ) {
        try {
            Pageable pageable = new Pager( page, size );
            CriteriaImpl<SysUser> criteria = sysUserService.getCriteria();

            criteria.useAlias( "t7" )
                    .query( SysUser::getId, SysUser::getState, SysUser::getPassword, SysUser::getUserName )
                    .template( "LENGTH({}) > {}", SysUser::getUserName, 10 )
                    .orTemplate( "LEFT({}, 3) = {}", SysUser::getPassword, "123" )
                    .orTemplate( "RIGHT({}, 1) = {}", SysUser::getUserName, "另" )
                    .exactTemplate( "OR LEFT({}, 1) = {}", SysUser::getCreateUser, "神" );
            Result result = new DataResult( "list", sysUserService.pageableList( pageable, criteria ) );
            result.add( "pageable", pageable );
            return result;
        } catch ( Exception e ) {
            log.error( "系统出现异常：", e );
            return new DataResult( e );
        }
    }
}
