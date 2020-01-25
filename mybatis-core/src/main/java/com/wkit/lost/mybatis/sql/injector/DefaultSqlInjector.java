package com.wkit.lost.mybatis.sql.injector;

import com.wkit.lost.mybatis.sql.injector.methods.BatchDelete;
import com.wkit.lost.mybatis.sql.injector.methods.BatchDeleteByPrimaryKey;
import com.wkit.lost.mybatis.sql.injector.methods.BatchInsert;
import com.wkit.lost.mybatis.sql.injector.methods.BatchInsertNotWithAudit;
import com.wkit.lost.mybatis.sql.injector.methods.Count;
import com.wkit.lost.mybatis.sql.injector.methods.Delete;
import com.wkit.lost.mybatis.sql.injector.methods.DeleteByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.DeleteByPrimaryKey;
import com.wkit.lost.mybatis.sql.injector.methods.Exists;
import com.wkit.lost.mybatis.sql.injector.methods.ExistsByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.ExistsByPrimaryKey;
import com.wkit.lost.mybatis.sql.injector.methods.Insert;
import com.wkit.lost.mybatis.sql.injector.methods.InsertNotWithNull;
import com.wkit.lost.mybatis.sql.injector.methods.List;
import com.wkit.lost.mybatis.sql.injector.methods.ListByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.ListByEntities;
import com.wkit.lost.mybatis.sql.injector.methods.ListByEntity;
import com.wkit.lost.mybatis.sql.injector.methods.ArrayList;
import com.wkit.lost.mybatis.sql.injector.methods.MapList;
import com.wkit.lost.mybatis.sql.injector.methods.ObjectList;
import com.wkit.lost.mybatis.sql.injector.methods.LogicDelete;
import com.wkit.lost.mybatis.sql.injector.methods.LogicDeleteByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.MixinUpdateNotWithNull;
import com.wkit.lost.mybatis.sql.injector.methods.PageableList;
import com.wkit.lost.mybatis.sql.injector.methods.PageableListByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.SelectOne;
import com.wkit.lost.mybatis.sql.injector.methods.Update;
import com.wkit.lost.mybatis.sql.injector.methods.UpdateByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.UpdateNotWithLocking;
import com.wkit.lost.mybatis.sql.injector.methods.UpdateNotWithNull;
import com.wkit.lost.mybatis.sql.injector.methods.UpdateNotWithNullAndLocking;
import com.wkit.lost.mybatis.sql.method.Method;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 默认SQL注入器
 * @author wvkity
 */
public class DefaultSqlInjector extends AbstractSqlInjector {

    @Override
    public Collection<Method> getMethodListOfInjection() {
        return Stream.of(
                // 查询
                new Exists(),
                new ExistsByPrimaryKey(),
                new ExistsByCriteria(),
                new Count(),
                new SelectOne(),
                new List(),
                new ListByEntity(),
                new ListByEntities(),
                new ListByCriteria(),
                new ObjectList(),
                new ArrayList(),
                new MapList(),
                new PageableList(),
                new PageableListByCriteria(),
                // 保存
                new Insert(),
                new InsertNotWithNull(),
                new BatchInsert(),
                new BatchInsertNotWithAudit(),
                // 修改
                new Update(),
                new UpdateNotWithNull(),
                new MixinUpdateNotWithNull(),
                new UpdateByCriteria(),
                new UpdateNotWithLocking(),
                new UpdateNotWithNullAndLocking(),
                // 删除
                new Delete(),
                new DeleteByPrimaryKey(),
                new DeleteByCriteria(),
                new LogicDelete(),
                new LogicDeleteByCriteria(),
                new BatchDelete(),
                new BatchDeleteByPrimaryKey()
        ).collect( Collectors.toSet() );
    }
}
