package com.wkit.lost.mybatis.core.injector;

import com.wkit.lost.mybatis.core.injector.execute.BatchDelete;
import com.wkit.lost.mybatis.core.injector.execute.BatchDeleteById;
import com.wkit.lost.mybatis.core.injector.execute.BatchInsert;
import com.wkit.lost.mybatis.core.injector.execute.BatchInsertNotWithAudit;
import com.wkit.lost.mybatis.core.injector.execute.Count;
import com.wkit.lost.mybatis.core.injector.execute.Delete;
import com.wkit.lost.mybatis.core.injector.execute.DeleteByCriteria;
import com.wkit.lost.mybatis.core.injector.execute.DeleteById;
import com.wkit.lost.mybatis.core.injector.execute.Exists;
import com.wkit.lost.mybatis.core.injector.execute.ExistsById;
import com.wkit.lost.mybatis.core.injector.execute.Insert;
import com.wkit.lost.mybatis.core.injector.execute.InsertNotWithNull;
import com.wkit.lost.mybatis.core.injector.execute.List;
import com.wkit.lost.mybatis.core.injector.execute.ListByCriteria;
import com.wkit.lost.mybatis.core.injector.execute.ListByEntity;
import com.wkit.lost.mybatis.core.injector.execute.LogicDelete;
import com.wkit.lost.mybatis.core.injector.execute.LogicDeleteByCriteria;
import com.wkit.lost.mybatis.core.injector.execute.MixinUpdateNotWithNull;
import com.wkit.lost.mybatis.core.injector.execute.SelectOne;
import com.wkit.lost.mybatis.core.injector.execute.Update;
import com.wkit.lost.mybatis.core.injector.execute.UpdateNotWithLocking;
import com.wkit.lost.mybatis.core.injector.execute.UpdateNotWithNull;
import com.wkit.lost.mybatis.core.injector.execute.UpdateNotWithNullAndLocking;
import com.wkit.lost.mybatis.core.injector.method.Method;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统系统默认SQL注入器
 * @author wvkity
 */
public class DefaultInjector extends AbstractInjector {

    @Override
    public Collection<Method> getMethodsForInject( Class<?> mapperInterface ) {
        return Stream.of(
                // insert
                new Insert(),
                new InsertNotWithNull(),
                new BatchInsert(),
                new BatchInsertNotWithAudit(),
                // delete
                new Delete(),
                new DeleteById(),
                new DeleteByCriteria(),
                new BatchDeleteById(),
                new BatchDelete(),
                new LogicDelete(),
                new LogicDeleteByCriteria(),
                // update
                new Update(),
                new UpdateNotWithNull(),
                new UpdateNotWithLocking(),
                new UpdateNotWithNullAndLocking(),
                new MixinUpdateNotWithNull(),
                // query
                new Count(),
                new Exists(),
                new ExistsById(),
                new SelectOne(),
                new List(),
                new ListByEntity(),
                new ListByCriteria()
        ).collect( Collectors.toList() );
    }
}
