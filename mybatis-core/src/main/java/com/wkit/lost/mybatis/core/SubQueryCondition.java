package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.lambda.LambdaResolver;

/**
 * 子查询条件
 * <p>
 *     Example:<br>
 *     <ul>
 *         <li>SELECT * FROM MST WHERE MST.USER_ID IN (SELECT USER_ID FROM SCT WHERE SCT.TYPE = ?)</li>
 *         <li>SELECT * FROM MST WHERE EXISTS (SELECT USER_ID FROM SCT WHERE SCT.USER_ID = MST.USER_ID)</li>
 *         <li>SELECT * FROM MST WHERE MST.STATE = ? AND MST.USER_ID = (SELECT USER_ID FROM SCT WHERE SCT.TYPE = ? AND SCT.STATUS = ?)</li>
 *     </ul>
 * </p>
 * @param <T>       泛型类型
 * @param <Context> 当前对象
 * @param <R>       lambda对象
 * @author wvkity
 */
public interface SubQueryCondition<T, Context, R> extends CriteriaSearch, LambdaResolver<R> {

    /**
     * 主键等于
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context idEqFromSub( String subTempTabAlias ) {
        return idEq( searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 住建等于
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    Context idEq( SubCriteria<?> subCriteria );

    /**
     * 或主键等于
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orIdEqFromSub( String subTempTabAlias ) {
        return orIdEq( searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或主键等于
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    Context orIdEq( SubCriteria<?> subCriteria );

    /**
     * 等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context eqFromSub( R property, String subTempTabAlias ) {
        return eq( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context eqFromSub( String property, String subTempTabAlias ) {
        return eq( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context eq( R property, SubCriteria<?> subCriteria ) {
        return eq( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context eq( String property, SubCriteria<?> subCriteria );

    /**
     * 或等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orEqFromSub( R property, String subTempTabAlias ) {
        return orEq( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orEqFromSub( String property, String subTempTabAlias ) {
        return orEq( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context orEq( R property, SubCriteria<?> subCriteria ) {
        return orEq( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 或等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context orEq( String property, SubCriteria<?> subCriteria );

    /**
     * 不等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context neFromSub( R property, String subTempTabAlias ) {
        return ne( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 不等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context neFromSub( String property, String subTempTabAlias ) {
        return ne( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 不等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context ne( R property, SubCriteria<?> subCriteria ) {
        return ne( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 不等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context ne( String property, SubCriteria<?> subCriteria );

    /**
     * 或不等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orNeFromSub( R property, String subTempTabAlias ) {
        return orNe( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或不等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orNeFromSub( String property, String subTempTabAlias ) {
        return orNe( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或不等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context orNe( R property, SubCriteria<?> subCriteria ) {
        return orNe( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 或不等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context orNe( String property, SubCriteria<?> subCriteria );

    /**
     * 小于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context ltFromSub( R property, String subTempTabAlias ) {
        return lt( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 小于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context ltFromSub( String property, String subTempTabAlias ) {
        return lt( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 小于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context lt( R property, SubCriteria<?> subCriteria ) {
        return lt( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 小于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context lt( String property, SubCriteria<?> subCriteria );

    /**
     * 或小于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orLtFromSub( R property, String subTempTabAlias ) {
        return orLt( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或小于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orLtFromSub( String property, String subTempTabAlias ) {
        return orLt( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或小于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context orLt( R property, SubCriteria<?> subCriteria ) {
        return orLt( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 或小于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context orLt( String property, SubCriteria<?> subCriteria );

    /**
     * 小于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context leFromSub( R property, String subTempTabAlias ) {
        return le( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 小于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context leFromSub( String property, String subTempTabAlias ) {
        return le( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 小于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context le( R property, SubCriteria<?> subCriteria ) {
        return le( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 小于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context le( String property, SubCriteria<?> subCriteria );

    /**
     * 或小于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orLeFromSub( R property, String subTempTabAlias ) {
        return orLe( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或小于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orLeFromSub( String property, String subTempTabAlias ) {
        return orLe( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或小于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context orLe( R property, SubCriteria<?> subCriteria ) {
        return orLe( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 或小于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context orLe( String property, SubCriteria<?> subCriteria );

    /**
     * 大于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context gtFromSub( R property, String subTempTabAlias ) {
        return gt( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 大于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context gtFromSub( String property, String subTempTabAlias ) {
        return gt( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 大于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context gt( R property, SubCriteria<?> subCriteria ) {
        return gt( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 大于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context gt( String property, SubCriteria<?> subCriteria );

    /**
     * 或大于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orGtFromSub( R property, String subTempTabAlias ) {
        return orGt( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或大于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orGtFromSub( String property, String subTempTabAlias ) {
        return orGt( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或大于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context orGt( R property, SubCriteria<?> subCriteria ) {
        return orGt( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 或大于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context orGt( String property, SubCriteria<?> subCriteria );

    /**
     * 大于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context geFromSub( R property, String subTempTabAlias ) {
        return ge( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 大于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context geFromSub( String property, String subTempTabAlias ) {
        return ge( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 大于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context ge( R property, SubCriteria<?> subCriteria ) {
        return ge( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 大于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context ge( String property, SubCriteria<?> subCriteria );

    /**
     * 或大于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orGeFromSub( R property, String subTempTabAlias ) {
        return orGe( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或大于等于
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orGeFromSub( String property, String subTempTabAlias ) {
        return orGe( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * 或大于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context orGe( R property, SubCriteria<?> subCriteria ) {
        return orGe( lambdaToProperty( property ), subCriteria );
    }

    /**
     * 或大于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context orGe( String property, SubCriteria<?> subCriteria );

    /**
     * IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context inFromSub( R property, String subTempTabAlias ) {
        return in( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context inFromSub( String property, String subTempTabAlias ) {
        return in( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * IN范围
     * @param property    属性
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    default Context in( R property, SubCriteria<?> subCriteria ) {
        return in( lambdaToProperty( property ), subCriteria );
    }

    /**
     * IN范围
     * @param property    属性
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    Context in( String property, SubCriteria<?> subCriteria );

    /**
     * OR IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orInFromSub( R property, String subTempTabAlias ) {
        return orIn( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * OR IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orInFromSub( String property, String subTempTabAlias ) {
        return orIn( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * OR IN范围
     * @param property    属性
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    default Context orIn( R property, SubCriteria<?> subCriteria ) {
        return orIn( lambdaToProperty( property ), subCriteria );
    }

    /**
     * OR IN范围
     * @param property    属性
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    Context orIn( String property, SubCriteria<?> subCriteria );

    /**
     * NOT IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context notInFromSub( R property, String subTempTabAlias ) {
        return notIn( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * NOT IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context notInFromSub( String property, String subTempTabAlias ) {
        return notIn( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * NOT IN范围
     * @param property    属性
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    default Context notIn( R property, SubCriteria<?> subCriteria ) {
        return notIn( lambdaToProperty( property ), subCriteria );
    }

    /**
     * NOT IN范围
     * @param property    属性
     * @param subCriteria 子查询对象
     * @return 当前对象
     */
    Context notIn( String property, SubCriteria<?> subCriteria );

    /**
     * OR NOT IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orNotInFromSub( R property, String subTempTabAlias ) {
        return orNotIn( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * OR NOT IN范围
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context orNotInFromSub( String property, String subTempTabAlias ) {
        return orNotIn( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * OR NOT IN范围
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context orNotIn( R property, SubCriteria<?> subCriteria ) {
        return orNotIn( lambdaToProperty( property ), subCriteria );
    }

    /**
     * OR NOT IN范围
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context orNotIn( String property, SubCriteria<?> subCriteria );

    /**
     * EXISTS
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context exists( String subTempTabAlias ) {
        return exists( searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * EXISTS
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context exists( SubCriteria<?> subCriteria ) {
        return exists( ( String ) null, subCriteria );
    }

    /**
     * EXISTS
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context exists( R property, String subTempTabAlias ) {
        return exists( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * EXISTS
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context exists( String property, String subTempTabAlias ) {
        return exists( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * EXISTS
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context exists( R property, SubCriteria<?> subCriteria ) {
        return exists( lambdaToProperty( property ), subCriteria );
    }

    /**
     * EXISTS
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context exists( String property, SubCriteria<?> subCriteria );

    /**
     * NOT EXISTS
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context notExists( String subTempTabAlias ) {
        return notExists( searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * NOT EXISTS
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context notExists( SubCriteria<?> subCriteria ) {
        return notExists( ( String ) null, subCriteria );
    }

    /**
     * NOT EXISTS
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context notExists( R property, String subTempTabAlias ) {
        return notExists( lambdaToProperty( property ), searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * NOT EXISTS
     * @param property        属性
     * @param subTempTabAlias 子查询别名
     * @return 当前对象
     */
    default Context notExists( String property, String subTempTabAlias ) {
        return notExists( property, searchSubCriteria( subTempTabAlias ) );
    }

    /**
     * NOT EXISTS
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    default Context notExists( R property, SubCriteria<?> subCriteria ) {
        return notExists( lambdaToProperty( property ), subCriteria );
    }

    /**
     * NOT EXISTS
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Context notExists( String property, SubCriteria<?> subCriteria );
}
