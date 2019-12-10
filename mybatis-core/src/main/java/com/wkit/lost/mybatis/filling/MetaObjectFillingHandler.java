package com.wkit.lost.mybatis.filling;

import com.wkit.lost.mybatis.annotation.extension.FillingRule;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Optional;

/**
 * 元对象字段自动填充值处理接口
 * @author wvkity
 */
public interface MetaObjectFillingHandler {

    /**
     * 保存操作填充值
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     * @return 当前对象
     * @see #fillingValue(MetaObject, String, Object, FillingRule)
     */
    default MetaObjectFillingHandler insertFillingValue( MetaObject metaObject, String property, Object value ) {
        return fillingValue( metaObject, property, value, FillingRule.INSERT );
    }

    /**
     * 更新操作填充值
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     * @return 当前对象
     * @see #fillingValue(MetaObject, String, Object, FillingRule)
     */
    default MetaObjectFillingHandler updateFillingValue( MetaObject metaObject, String property, Object value ) {
        return fillingValue( metaObject, property, value, FillingRule.UPDATE );
    }

    /**
     * 逻辑删除操作填充值
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     * @return 当前对象
     * @see #fillingValue(MetaObject, String, Object, FillingRule)
     */
    default MetaObjectFillingHandler deleteFillingValue( MetaObject metaObject, String property, Object value ) {
        return fillingValue( metaObject, property, value, FillingRule.DELETE );
    }

    /**
     * 填充值
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     * @return 当前对象
     */
    default MetaObjectFillingHandler fillingValue( MetaObject metaObject, String property, Object value ) {
        if ( metaObject != null && value != null ) {
            if ( metaObject.hasSetter( property ) && metaObject.hasGetter( property ) ) {
                if ( metaValueIsEmpty( metaObject, property ) && canFilling( metaObject, property, value ) ) {
                    metaObject.setValue( property, value );
                }
            } else if ( metaObject.hasGetter( Constants.PARAM_ENTITY ) ) {
                Object entity = metaObject.getValue( Constants.PARAM_ENTITY );
                if ( entity != null ) {
                    // SystemMetaObject.forObject( entity )
                    return fillingValue( MetaObjectUtil.forObject( entity ), property, value );
                }
            }
        }
        return this;
    }

    /**
     * 填充值
     * <p>根据指定填充规则校验是否可自动填充</p>
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     * @param rule       填充规则
     * @return 当前对象
     */
    default MetaObjectFillingHandler fillingValue( MetaObject metaObject, String property, Object value, FillingRule rule ) {
        if ( metaObject != null && value != null ) {
            if ( metaObject.hasSetter( property ) && metaObject.hasGetter( property ) ) {
                if ( metaValueIsEmpty( metaObject, property ) && canFilling( metaObject, property, value, rule ) ) {
                    metaObject.setValue( property, value );
                }
            } else if ( metaObject.hasGetter( Constants.PARAM_ENTITY ) ) {
                Object entity = metaObject.getValue( Constants.PARAM_ENTITY );
                if ( entity != null ) {
                    // SystemMetaObject.forObject( entity )
                    return fillingValue( MetaObjectUtil.forObject( entity ), property, value, rule );
                }
            }
        }
        return this;
    }

    /**
     * 检查元对象中的属性值是否为空
     * @param metaObject 元对象
     * @param property   属性
     * @return true: 是 false: 否
     */
    default boolean metaValueIsEmpty( MetaObject metaObject, String property ) {
        if ( metaObject != null && !Ascii.isNullOrEmpty( property ) ) {
            if ( metaObject.hasGetter( property ) ) {
                return metaObject.getValue( property ) == null;
            }
        }
        return false;
    }

    /**
     * 检查是否可填充值(值类型必须匹配)
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     * @return true: 是 false: 否
     */
    default boolean canFilling( MetaObject metaObject, String property, Object value ) {
        Table table = getTable( metaObject );
        if ( table != null ) {
            Optional<Column> first = table.search( property );
            if ( first.isPresent() ) {
                Column column = first.get();
                return ( column.isInsertable() || column.isUpdatable() ) 
                        && column.getJavaType().isAssignableFrom( value.getClass() );
            }
        }
        return false;
    }

    /**
     * 检查是否可填充值(值类型、填充规则必须匹配)
     * @param metaObject 元对象
     * @param property   属性
     * @param value      值
     * @param rule       填充规则
     * @return true: 是 false: 否
     */
    default boolean canFilling( MetaObject metaObject, String property, Object value, FillingRule rule ) {
        Table table = getTable( metaObject );
        if ( table != null ) {
            Optional<Column> first = table.search( property );
            if ( first.isPresent() ) {
                Column column = first.get();
                return column.getJavaType().isAssignableFrom( value.getClass() ) && column.canFilling( rule );
            }
        }
        return false;
    }

    /**
     * 获取表映射对象信息
     * @param metaObject 元对象
     * @return 表映射对象
     */
    default Table getTable( MetaObject metaObject ) {
        Table table = null;
        if ( metaObject != null ) {
            table = EntityHandler.getTable( metaObject.getOriginalObject().getClass() );
            if ( table == null && metaObject.hasGetter( Constants.PARAM_ENTITY ) ) {
                Object entity = metaObject.getValue( Constants.PARAM_ENTITY );
                if ( entity != null ) {
                    // SystemMetaObject.forObject( entity )
                    return getTable( MetaObjectUtil.forObject( entity ) );
                }
            }
        }
        return table;
    }

    /**
     * 获取字段信息
     * @param metaObject 元对象
     * @param property   属性
     * @return 字段信息
     */
    default Column getColumn( MetaObject metaObject, String property ) {
        Table table = getTable( metaObject );
        if ( table != null ) {
            Optional<Column> optional = table.search( property );
            if ( optional.isPresent() ) {
                return optional.get();
            }
        }
        return null;
    }

    /**
     * 保存操作填充值
     * @param metaObject 元对象
     */
    void insertFilling( MetaObject metaObject );

    /**
     * 更新操作填充值
     * @param metaObject 元对象
     */
    void updateFilling( MetaObject metaObject );

    /**
     * 逻辑删除操作填充值
     * @param metaObject 元对象
     */
    void deleteFilling( MetaObject metaObject );

    /**
     * 是否开启保存操作自动填充值
     * @return true: 是 false: 否
     */
    boolean enableInsert();

    /**
     * 是否开启更新操作自动填充值
     * @return true: 是 false: 否
     */
    boolean enableUpdate();

    /**
     * 是否开启逻辑删除操作自动填充值
     * @return true: 是 false: 否
     */
    boolean enableDelete();

    /**
     * 是否开启默认匹配模式
     * @return true: 是 false: 否
     */
    boolean enableAutoMatching();
}
