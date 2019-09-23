package com.wkit.lost.mybatis.javax;

/**
 * javax.persistence-api相关注解类名
 * @author DT
 */
public abstract class JavaxPersistence {

    /**
     * javax.persistence-api中的{@code @Table}注解
     */
    public static final String TABLE = "javax.persistence.Table";

    /**
     * javax.persistence-api中的{@code @Entity}注解
     */
    public static final String ENTITY = "java.persistence.Entity";

    /**
     * javax.persistence-api中的{@code @Column}注解
     */
    public static final String COLUMN = "javax.persistence.Column";

    /**
     * javax.persistence-api中的{@code @Transient}注解
     */
    public static final String TRANSIENT = "javax.persistence.Transient";

    /**
     * javax.persistence-api中的{@code @Id}注解
     */
    public static final String ID = "javax.persistence.Id";

    /**
     * javax.persistence-api中的{@code @OrderBy}注解
     */
    public static final String ORDER_BY = "javax.persistence.OrderBy";

    /**
     * javax.persistence-api中的{@code @SequenceGenerator}注解
     */
    public static final String SEQUENCE_GENERATOR = "javax.persistence.SequenceGenerator";

    /**
     * javax.persistence-api中的{@code @GeneratedValue}注解
     */
    public static final String GENERATED_VALUE = "javax.persistence.GeneratedValue";
}
