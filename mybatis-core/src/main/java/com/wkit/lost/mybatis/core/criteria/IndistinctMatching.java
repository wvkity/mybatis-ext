package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.lambda.LambdaConverter;

import java.io.Serializable;

/**
 * 模糊匹配接口
 * @param <Context> 当前对象
 * @param <R>       lambda属性对象
 */
public interface IndistinctMatching<Context, R> extends LambdaConverter<R>, Segment, Serializable {
    
    /**
     * 模糊匹配
     * <p>LIKE '%value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context like( R property, String value ) {
        return like( lambdaToProperty( property ), value );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 当前对象
     */
    default Context like( R property, String value, MatchMode matchMode ) {
        return like( lambdaToProperty( property ), value, matchMode );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE '%value%' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context like( R property, String value, Character escape ) {
        return like( lambdaToProperty( property ), value, escape );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 当前对象
     */
    default Context like( R property, String value, MatchMode matchMode, Character escape ) {
        return like( lambdaToProperty( property ), value, matchMode, escape );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE '%value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context like( String property, String value ) {
        return like( property, value, MatchMode.ANYWHERE );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 当前对象
     */
    default Context like( String property, String value, MatchMode matchMode ) {
        return like( property, value, matchMode, null );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE '%value%' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context like( String property, String value, Character escape ) {
        return like( property, value, MatchMode.ANYWHERE, escape );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 当前对象
     */
    Context like( String property, String value, MatchMode matchMode, Character escape );
    
    /**
     * 模糊匹配
     * <p>LIKE '%value'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context likeLeft( R property, String value ) {
        return likeLeft( lambdaToProperty( property ), value );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE '%value'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context likeLeft( R property, String value, Character escape ) {
        return likeLeft( lambdaToProperty( property ), value, escape );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE '%value'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context likeLeft( String property, String value ) {
        return like( property, value, MatchMode.END );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE '%value'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context likeLeft( String property, String value, Character escape ) {
        return like( property, value, MatchMode.END, escape );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE 'value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context likeRight( R property, String value ) {
        return likeRight( lambdaToProperty( property ), value );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE 'value%'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context likeRight( R property, String value, Character escape ) {
        return likeRight( lambdaToProperty( property ), value, escape );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE 'value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context likeRight( String property, String value ) {
        return like( property, value, MatchMode.START );
    }
    
    /**
     * 模糊匹配
     * <p>LIKE 'value%'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context likeRight( String property, String value, Character escape ) {
        return like( property, value, MatchMode.START, escape );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE '%value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLike( R property, String value ) {
        return orLike( lambdaToProperty( property ), value );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 当前对象
     */
    default Context orLike( R property, String value, MatchMode matchMode ) {
        return orLike( lambdaToProperty( property ), value, matchMode );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE '%value%' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context orLike( R property, String value, Character escape ) {
        return orLike( lambdaToProperty( property ), value, escape );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 当前对象
     */
    default Context orLike( R property, String value, MatchMode matchMode, Character escape ) {
        return orLike( lambdaToProperty( property ), value, matchMode, escape );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE '%value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLike( String property, String value ) {
        return orLike( property, value, MatchMode.ANYWHERE );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 当前对象
     */
    default Context orLike( String property, String value, MatchMode matchMode ) {
        return orLike( property, value, matchMode, null );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE '%value%' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context orLike( String property, String value, Character escape ) {
        return orLike( property, value, MatchMode.ANYWHERE, escape );
    }
    
    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 当前对象
     */
    Context orLike( String property, String value, MatchMode matchMode, Character escape );
    
    /**
     * 模糊匹配
     * <p>LIKE '%value'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLikeLeft( R property, String value ) {
        return orLikeLeft( lambdaToProperty( property ), value );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE '%value' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context orLikeLeft( R property, String value, Character escape ) {
        return orLikeLeft( lambdaToProperty( property ), value, escape );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE '%value'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLikeLeft( String property, String value ) {
        return orLike( property, value, MatchMode.END );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE '%value' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context orLikeLeft( String property, String value, Character escape ) {
        return orLike( property, value, MatchMode.END, escape );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE 'value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLikeRight( R property, String value ) {
        return orLikeRight( lambdaToProperty( property ), value );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE 'value%' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context orLikeRight( R property, String value, Character escape ) {
        return orLikeRight( lambdaToProperty( property ), value, escape );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE 'value%'</p>
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLikeRight( String property, String value ) {
        return orLike( property, value, MatchMode.START );
    }
    
    /**
     * 模糊匹配
     * <p>OR LIKE 'value%' ESCAPE 'charValue'</p>
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 当前对象
     */
    default Context orLikeRight( String property, String value, Character escape ) {
        return orLike( property, value, MatchMode.START, escape );
    }
}
