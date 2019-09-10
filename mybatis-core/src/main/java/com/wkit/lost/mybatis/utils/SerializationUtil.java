package com.wkit.lost.mybatis.utils;

import com.wkit.lost.mybatis.exception.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 序列化工具
 * @author DT
 */
public class SerializationUtil {

    public SerializationUtil() {
        super();
    }

    /**
     * 深复制对象
     * @param object 目标对象
     * @param <T>    类型
     * @return 新的对象
     */
    public static <T extends Serializable> T clone( final T object ) {
        if ( object == null ) {
            return null;
        }
        final String objectData = "";
        return null;
    }

    /**
     * 序列化对象
     * @param object       对象
     * @param outputStream 输出流对象
     */
    public static void serialize( final Serializable object, final OutputStream outputStream ) {
        isTrue( outputStream != null, "The OutputStream must not be null" );
        try ( ObjectOutputStream out = new ObjectOutputStream( outputStream ) ) {
            out.writeObject( object );
        } catch ( IOException e ) {
            throw new SerializationException( e );
        }
    }

    /**
     * 序列化对象
     * @param object 对象
     * @return 内存字节流
     */
    public static byte[] serialize( final Serializable object ) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream( 512 );
        serialize( object, outputStream );
        return outputStream.toByteArray();
    }

    /**
     * 反序列化
     * @param inputStream 输入流
     * @param <T>         类型
     * @return 反序列化对象
     */
    @SuppressWarnings( "unchecked" )
    public static <T> T deserialize( final InputStream inputStream ) {
        isTrue( inputStream != null, "The InputStream must not be null" );
        try ( ObjectInputStream in = new ObjectInputStream( inputStream ) ) {
            return (T) in.readObject();
        } catch ( IOException | ClassNotFoundException e ) {
            throw new SerializationException( e );
        }
    }

    /**
     * 反序列化
     * @param objectData 对象字节数据
     * @param <T>        类型
     * @return 反序列化对象
     */
    public static <T> T deserialize( final byte[] objectData ) {
        isTrue( objectData != null, "The byte[] must not be null" );
        return deserialize( new ByteArrayInputStream( objectData ) );
    }


    static class ClassLoaderAwareObjectInputStream extends ObjectInputStream {

        private static final Map<String, Class<?>> PRIMITIVE_TYPE_CACHE = new HashMap<>();
        
        static {
            PRIMITIVE_TYPE_CACHE.put("byte", byte.class);
            PRIMITIVE_TYPE_CACHE.put("short", short.class);
            PRIMITIVE_TYPE_CACHE.put("int", int.class);
            PRIMITIVE_TYPE_CACHE.put("long", long.class);
            PRIMITIVE_TYPE_CACHE.put("float", float.class);
            PRIMITIVE_TYPE_CACHE.put("double", double.class);
            PRIMITIVE_TYPE_CACHE.put("boolean", boolean.class);
            PRIMITIVE_TYPE_CACHE.put("char", char.class);
            PRIMITIVE_TYPE_CACHE.put("void", void.class);
        }

        private final ClassLoader classLoader;

        /**
         * 构造方法
         * @param in          输入流对象
         * @param classLoader 类加载器
         * @throws IOException if an I/O error occurs while reading stream header.
         * @see ObjectInputStream
         */
        public ClassLoaderAwareObjectInputStream( InputStream in, ClassLoader classLoader ) throws IOException {
            super( in );
            this.classLoader = classLoader;
        }

        @Override
        protected Class<?> resolveClass( ObjectStreamClass desc ) throws ClassNotFoundException {
            final String name = desc.getName();
            try {
                return Class.forName( name, false, classLoader );
            } catch ( ClassNotFoundException e ) {
                try {
                    return Class.forName( name, false, Thread.currentThread().getContextClassLoader() );
                } catch ( ClassNotFoundException ex ) {
                    final Class<?> clazz = PRIMITIVE_TYPE_CACHE.get( name );
                    if ( clazz != null ) {
                        return clazz;
                    }
                    throw ex;
                }
            }
        }
    }

    public static void isTrue( final boolean expression, final String message, final Object... values ) {
        if ( !expression ) {
            throw new IllegalArgumentException( String.format( message, values ) );
        }
    }
}
