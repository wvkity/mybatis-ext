package com.wvkity.mybatis.core.injector;

import com.wvkity.mybatis.core.injector.execute.ArrayList;
import com.wvkity.mybatis.core.injector.execute.ArrayPageableList;
import com.wvkity.mybatis.core.injector.execute.BatchDelete;
import com.wvkity.mybatis.core.injector.execute.BatchDeleteById;
import com.wvkity.mybatis.core.injector.execute.BatchInsert;
import com.wvkity.mybatis.core.injector.execute.BatchInsertNotWithAudit;
import com.wvkity.mybatis.core.injector.execute.Count;
import com.wvkity.mybatis.core.injector.execute.CountByCriteria;
import com.wvkity.mybatis.core.injector.execute.Delete;
import com.wvkity.mybatis.core.injector.execute.DeleteByCriteria;
import com.wvkity.mybatis.core.injector.execute.DeleteById;
import com.wvkity.mybatis.core.injector.execute.AllList;
import com.wvkity.mybatis.core.injector.execute.Exists;
import com.wvkity.mybatis.core.injector.execute.ExistsByCriteria;
import com.wvkity.mybatis.core.injector.execute.ExistsById;
import com.wvkity.mybatis.core.injector.execute.Insert;
import com.wvkity.mybatis.core.injector.execute.InsertNotWithNull;
import com.wvkity.mybatis.core.injector.execute.List;
import com.wvkity.mybatis.core.injector.execute.ListByCriteria;
import com.wvkity.mybatis.core.injector.execute.ListByEntities;
import com.wvkity.mybatis.core.injector.execute.ListByEntity;
import com.wvkity.mybatis.core.injector.execute.LogicDelete;
import com.wvkity.mybatis.core.injector.execute.LogicDeleteByCriteria;
import com.wvkity.mybatis.core.injector.execute.MapList;
import com.wvkity.mybatis.core.injector.execute.MapPageableList;
import com.wvkity.mybatis.core.injector.execute.MixinUpdateNotWithNull;
import com.wvkity.mybatis.core.injector.execute.ObjectList;
import com.wvkity.mybatis.core.injector.execute.ObjectPageableList;
import com.wvkity.mybatis.core.injector.execute.PageableList;
import com.wvkity.mybatis.core.injector.execute.PageableListByCriteria;
import com.wvkity.mybatis.core.injector.execute.SelectOne;
import com.wvkity.mybatis.core.injector.execute.SimpleList;
import com.wvkity.mybatis.core.injector.execute.Update;
import com.wvkity.mybatis.core.injector.execute.UpdateByCriteria;
import com.wvkity.mybatis.core.injector.execute.UpdateNotWithLocking;
import com.wvkity.mybatis.core.injector.execute.UpdateNotWithNull;
import com.wvkity.mybatis.core.injector.execute.UpdateNotWithNullAndLocking;
import com.wvkity.mybatis.core.injector.method.Method;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统系统默认SQL注入器
 * @author wvkity
 */
public class DefaultInjector extends AbstractInjector {

    @Override
    public Collection<Method> getMethodsForInject(Class<?> mapperInterface) {
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
                new UpdateByCriteria(),
                // query
                new Count(),
                new CountByCriteria(),
                new Exists(),
                new ExistsById(),
                new ExistsByCriteria(),
                new SelectOne(),
                new List(),
                new AllList(),
                new SimpleList(),
                new ObjectList(),
                new ArrayList(),
                new MapList(),
                new ObjectPageableList(),
                new ArrayPageableList(),
                new MapPageableList(),
                new ListByEntity(),
                new ListByEntities(),
                new ListByCriteria(),
                new PageableList(),
                new PageableListByCriteria()
        ).collect(Collectors.toList());
    }
}
