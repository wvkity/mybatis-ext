package com.wkit.lost.mybatis.plugins.dbs.dialect;

import com.wkit.lost.mybatis.plugins.config.ThreadLocalPageable;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.Optional;

/**
 * 抽象分页方言
 * @author DT
 */
public abstract class AbstractPageableDialect extends AbstractDialect implements PageableDialect {

    /**
     * 获取分页对象
     * @return {@link Pageable}
     */
    public Pageable getPageable() {
        return ThreadLocalPageable.getPageable();
    }

    @Override
    public boolean beforeOfQueryRecord( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return Optional.ofNullable( getPageable() ).map( pageable -> pageable.getSize() > 0 ).orElse( false );
    }

    @Override
    public boolean executePagingOnBefore( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        if ( isLimit() ) {
            return super.executePagingOnBefore( statement, parameter, rowBounds );
        } else {
            return Optional.ofNullable( getPageable() ).map( pageable -> pageable.getSize() > 0 ).orElse( false );
        }
    }

    @Override
    public boolean afterOfQueryRecord( long records, Object parameter, RowBounds rowBounds ) {
        Pageable pageable = getPageable();
        pageable.setRecord( records );
        if ( pageable.getSize() < 0 ) {
            return false;
        }
        return pageable.hasNext();
    }

}
