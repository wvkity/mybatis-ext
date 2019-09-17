package com.wkit.lost.mybatis.plugins.zconfig;

import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.plugins.utils.PageableUtil;
import com.wkit.lost.paging.Pageable;
import com.wkit.lost.paging.Pager;
import lombok.Getter;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * 分页配置
 * @author DT
 */
public class PageableConfig {

    /**
     * RowBounds参数offset是否作为Pages使用
     */
    @Getter
    private boolean offsetAsPage = false;

    /**
     * 获取分页对象
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @return 分页对象
     */
    @SuppressWarnings( "unchecked" )
    public Pageable getPageable( Object parameter, RowBounds rowBounds ) {
        // 从线程中获取
        Pageable pageable = ThreadLocalPageable.getPageable();
        if ( pageable != null ) {
            return pageable;
        }
        // 检测参数类型
        if ( parameter instanceof Pageable ) {
            pageable = ( Pageable ) parameter;
        } else if ( parameter instanceof Map ) {
            // 从map参数列表中加载
            Map<String, Object> map = ( Map<String, Object> ) parameter;
            boolean flag = map.containsKey( "pageable" ) && Optional.ofNullable( map.get( "pageable" ) )
                    .map( Object::getClass )
                    .map( Pageable.class::isAssignableFrom )
                    .orElse( false );
            if ( flag ) {
                pageable = ( Pageable ) map.get( "pageable" );
            } else {
                for ( Object arg : map.values() ) {
                    if ( arg instanceof Pageable ) {
                        pageable = ( Pageable ) arg;
                        break;
                    }
                }
            }
        } else {
            pageable = PageableUtil.getPageable( parameter );
        }
        if ( pageable == null && this.offsetAsPage ) {
            if ( rowBounds != RowBounds.DEFAULT ) {
                long offset = rowBounds.getOffset();
                long size = rowBounds.getLimit();
                long page = size != 0 ? ( long ) Math.ceil( ( ( double ) ( offset + size ) / size ) ) : 0;
                pageable = new Pager( page, size );
            }
        }
        if ( pageable != null ) {
            ThreadLocalPageable.setPageable( pageable );
        }
        return pageable;
    }

    /**
     * 设置属性
     * @param props {@link Properties}
     */
    public void setProperties( Properties props ) {
        this.offsetAsPage = StringUtil.toBoolean( props.getProperty( "offsetAsPage" ) );
    }

}
