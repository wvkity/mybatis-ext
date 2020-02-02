package com.wkit.lost.mybatis.core.criteria;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 联表方式
 * @author wvkity
 */
@Accessors( chain = true )
public class Foreign {

    /**
     * 主表属性
     */
    @Getter
    @Setter
    private String master;

    /**
     * 副表属性
     */
    @Getter
    @Setter
    private String foreign;

    /**
     * 连接模式
     */
    @Getter
    @Setter
    private JoinMode joinMode;

    /**
     * 构造方法
     * @param master 主表属性
     * @param foreign 副表属性 0
     */
    public Foreign( String master, String foreign ) {
        this( master, foreign, JoinMode.INNER );
    }

    /**
     * 构造方法
     * @param master 主表属性
     * @param foreign 副表属性
     * @param joinMode 连接模式
     */
    public Foreign( String master, String foreign, JoinMode joinMode ) {
        this.master = master;
        this.foreign = foreign;
        this.joinMode = joinMode;
    }
}
