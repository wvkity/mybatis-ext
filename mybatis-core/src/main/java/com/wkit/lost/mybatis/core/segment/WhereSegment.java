package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * WHERE条件SQL片段类
 * @author wvkity
 */
public class WhereSegment extends AbstractSegment {

    private static final long serialVersionUID = -1005264914827625587L;

    @Override
    public String getSqlSegment() {
        if ( isNotEmpty() ) {
            List<String> list = new ArrayList<>( segments.size() );
            for ( Segment segment : segments ) {
                String condition = segment.getSqlSegment();
                if ( StringUtil.hasText( condition ) ) {
                    list.add( condition );
                }
            }
            if ( !list.isEmpty() ) {
                return String.join( " ", list ).trim();
            }
        }
        return "";
    }

    /**
     * 根据属性名称获取值
     * @param property 属性名
     * @return 属性值
     */
    public Object getConditionValue( final String property ) {
        if ( Ascii.hasText( property ) && isNotEmpty() ) {
            for ( Segment segment : segments ) {
                Criterion<?> expression = ( Criterion<?> ) segment;
                if ( property.equals( expression.getProperty() ) ) {
                    return expression.getValue();
                }
            }
        }
        return null;
    }
}
