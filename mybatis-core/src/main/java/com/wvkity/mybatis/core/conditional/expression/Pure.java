package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.utils.StringUtil;

/**
 * 纯SQL条件
 * @author wvkity
 */
public class Pure extends DirectExpressionWrapper {

    private static final long serialVersionUID = 5228137555981602679L;

    /**
     * 纯SQL条件
     */
    private final String expression;

    /**
     * 构造方法
     * @param expression 条件
     */
    private Pure(String expression) {
        this.expression = expression;
    }

    @Override
    public String getSegment() {
        return this.expression;
    }

    /**
     * 创建纯SQL条件对象
     * @param expression 条件
     * @return 条件对象
     */
    public static Pure of(String expression) {
        if (StringUtil.isBlank(expression)) {
            return null;
        }
        return new Pure(expression);
    }
}
