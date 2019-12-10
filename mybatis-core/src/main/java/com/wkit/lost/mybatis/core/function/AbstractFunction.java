package com.wkit.lost.mybatis.core.function;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 抽象聚合函数
 * @author wvkity
 */
@Accessors( chain = true )
@SuppressWarnings( "serial" )
public abstract class AbstractFunction implements Aggregation {

    /**
     * 忽略获取字段信息属性
     */
    private static final Set<String> IGNORE_TRANSFORM = new HashSet<>( Arrays.asList( "*", "0", "1" ) );

    /**
     * 函数名
     */
    protected String name;

    /**
     * 属性
     */
    @Getter
    @Setter
    protected String property;

    /**
     * 逻辑操作
     */
    @Getter
    @Setter
    protected Logic logic = Logic.AND;

    /**
     * 操作符
     */
    @Getter
    @Setter
    protected Comparator comparator;

    /**
     * 别名
     */
    @Getter
    @Setter
    protected String alias;

    /**
     * 条件对象
     */
    @Getter
    @Setter
    protected Criteria<?> criteria;

    /**
     * 字段对象
     */
    @Getter
    protected Column column;

    /**
     * 是否去重
     */
    @Getter
    @Setter
    protected boolean distinct = false;

    /**
     * 小数位数
     */
    @Getter
    @Setter
    protected Integer scale;

    /**
     * 值
     */
    protected List<Object> values;

    @Override
    public String getSqlSegment() {
        return transform( false );
    }

    @Override
    public String toQuerySqlSegment() {
        return transform( true );
    }

    @Override
    public String toOrderSqlSegment() {
        if ( StringUtil.hasText( property ) ) {
            return getFunctionBody();
        }
        return "";
    }

    /**
     * 转换成SQL片段
     * @param isQuery 是否为查询片段
     * @return SQL片段
     */
    private String transform( boolean isQuery ) {
        if ( StringUtil.hasText( this.property ) ) {
            StringBuilder buffer = new StringBuilder( 50 );
            if ( !isQuery ) {
                buffer.append( this.logic.getSqlSegment() ).append( " " );
            }
            String functionName = getFunctionBody();
            if ( isQuery ) {
                buffer.append( functionName );
                // 查询别名
                if ( StringUtil.hasText( alias ) ) {
                    if ( alias.contains( "." ) ) {
                        buffer.append( " \"" ).append( alias ).append( "\"" );
                    } else {
                        buffer.append( " " ).append( alias );
                    }
                } else if ( hasScale() ) {
                    buffer.append( " " ).append( name );
                }
            } else {
                // having条件
                // 判断是否存在多个条件
                if ( CollectionUtil.hasElement( values ) ) {
                    if ( this.comparator.isSimple() ) {
                        buffer.append( functionName ).append( " " ).append( this.comparator.getSqlSegment() ).append( " #{" )
                                .append( criteria.defaultPlaceholder( this.values.get( 0 ) ) ).append( "}" );
                    } else {
                        // 获取前两个参数
                        List<String> placeholders = criteria.defaultPlaceholders( this.values.stream().limit( 2 ).collect( Collectors.toList() ) );
                        String first = "#{" + placeholders.get( 0 ) + "}";
                        String last = "#{" + placeholders.get( 1 ) + "}";
                        buffer.append( " " ).append( StringUtil.format( this.comparator.getSqlSegment(), first, functionName, functionName, last ) );
                    }
                } else {
                    throw new MyBatisException( "The parameter value cannot be empty." );
                }
            }
            return buffer.toString();
        }
        return "";
    }

    private String getFunctionBody() {
        StringBuilder buffer = new StringBuilder( 30 );
        boolean hasScale = hasScale();
        if ( hasScale ) {
            buffer.append( "CAST(" );
        }
        buffer.append( this.name ).append( "(" );
        if ( IGNORE_TRANSFORM.contains( this.property ) ) {
            buffer.append( this.property );
        } else {
            Column column = criteria.searchColumn( this.property );
            String tabAlias = criteria.isEnableAlias() ? ( criteria.getAlias() + "." ) : "";
            // 去重
            if ( this.distinct ) {
                buffer.append( "DISTINCT " );
            }
            buffer.append( tabAlias ).append( column.getColumn() );
        }
        buffer.append( ")" );
        if ( hasScale ) {
            buffer.append( " AS DECIMAL(38, " ).append( scale ).append( "))" );
        }
        return buffer.toString();
    }

    private boolean hasScale() {
        return scale != null && scale > -1;
    }

    @Override
    public AbstractFunction setValues( List<Object> values ) {
        this.values = values;
        return this;
    }

    @Override
    public AbstractFunction setValues( Object... values ) {
        return setValues( ArrayUtil.toList( values ) );
    }
}
