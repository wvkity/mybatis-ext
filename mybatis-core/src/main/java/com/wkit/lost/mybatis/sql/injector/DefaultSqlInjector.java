package com.wkit.lost.mybatis.sql.injector;

import com.wkit.lost.mybatis.sql.injector.methods.BatchDelete;
import com.wkit.lost.mybatis.sql.injector.methods.BatchDeleteByPrimaryKey;
import com.wkit.lost.mybatis.sql.injector.methods.Count;
import com.wkit.lost.mybatis.sql.injector.methods.Delete;
import com.wkit.lost.mybatis.sql.injector.methods.DeleteByPrimaryKey;
import com.wkit.lost.mybatis.sql.injector.methods.Exists;
import com.wkit.lost.mybatis.sql.injector.methods.ExistsByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.ExistsByPrimaryKey;
import com.wkit.lost.mybatis.sql.injector.methods.Insert;
import com.wkit.lost.mybatis.sql.injector.methods.InsertSelective;
import com.wkit.lost.mybatis.sql.injector.methods.List;
import com.wkit.lost.mybatis.sql.injector.methods.ListByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.ListByEntities;
import com.wkit.lost.mybatis.sql.injector.methods.ListByEntity;
import com.wkit.lost.mybatis.sql.injector.methods.ListForArray;
import com.wkit.lost.mybatis.sql.injector.methods.ListForMap;
import com.wkit.lost.mybatis.sql.injector.methods.ListForObject;
import com.wkit.lost.mybatis.sql.injector.methods.LogicDelete;
import com.wkit.lost.mybatis.sql.injector.methods.LogicDeleteByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.PageableList;
import com.wkit.lost.mybatis.sql.injector.methods.PageableListByCriteria;
import com.wkit.lost.mybatis.sql.injector.methods.SelectOne;
import com.wkit.lost.mybatis.sql.injector.methods.Update;
import com.wkit.lost.mybatis.sql.injector.methods.UpdateSelective;
import com.wkit.lost.mybatis.sql.method.Method;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 默认SQL注入器
 * @author DT
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
                new ListForObject(),
                new ListForArray(),
                new ListForMap(),
                new PageableList(),
                new PageableListByCriteria(),
                // 保存
                new Insert(),
                new InsertSelective(),
                // 修改
                new Update(),
                new UpdateSelective(),
                // 删除
                new Delete(),
                new DeleteByPrimaryKey(),
                new LogicDelete(),
                new LogicDeleteByCriteria(),
                new BatchDelete(),
                new BatchDeleteByPrimaryKey()
        ).collect( Collectors.toSet() );
    }
}
