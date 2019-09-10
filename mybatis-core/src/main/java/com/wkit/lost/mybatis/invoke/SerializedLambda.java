package com.wkit.lost.mybatis.invoke;

import com.wkit.lost.mybatis.utils.SerializationUtil;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.lambda.Property;
import lombok.Getter;

import java.io.*;

/**
 * {@link java.lang.invoke.SerializedLambda}
 * @author DT
 */
@Getter
public class SerializedLambda implements Serializable {

    private static final long serialVersionUID = 8025925345765570181L;

    private Class<?> capturingClass;
    private String functionalInterfaceClass;
    private String functionalInterfaceMethodName;
    private String functionalInterfaceMethodSignature;
    private String implClass;
    private String implMethodName;
    private String implMethodSignature;
    private int implMethodKind;
    private String instantiatedMethodType;
    private Object[] capturedArgs;

    /**
     * 通过反序列化转换Class
     * @param lambda {@link Property}对象
     * @param <T>    类型
     * @param <R>    返回值类型
     * @return {@link SerializedLambda}对象
     */
    public static <T, R> SerializedLambda convert( Property<T, R> lambda ) {
        byte[] bytes = SerializationUtil.serialize( lambda );
        try ( ObjectInputStream inputStream = new ObjectInputStream( new ByteArrayInputStream( bytes ) ) {
            @Override
            protected Class<?> resolveClass( ObjectStreamClass desc ) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass( desc );
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        } ) {
            return ( SerializedLambda ) inputStream.readObject();
        } catch ( ClassNotFoundException | IOException e ) {
            throw new MyBatisException( e.getMessage(), e );
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                this.implClass.replace( "/", "." ) +
                "#" + this.implMethodName;
    }

}
