package com.wvkity.mybatis.binding;

import com.wvkity.mybatis.utils.ClassUtil;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 映射方法类
 * <p>重写{@link org.apache.ibatis.binding.MapperMethod}</p>
 * @author wvkity
 */
public class MyBatisMapperMethod {

    /**
     * SQL指令
     */
    private final SqlCommand command;
    /**
     * 方法
     */
    private final MethodSignature method;

    /**
     * 构造方法
     * @param mapperInterface 接口
     * @param method          方法
     * @param configuration   配置
     */
    public MyBatisMapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        this.command = new SqlCommand(configuration, mapperInterface, method);
        this.method = new MethodSignature(configuration, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        switch (this.command.getType()) {
            case INSERT: {
                Object param = this.method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.insert(this.command.getName(), param));
                break;
            }
            case UPDATE: {
                Object param = this.method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.update(this.command.getName(), param));
                break;
            }
            case DELETE: {
                Object param = this.method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.delete(this.command.getName(), param));
                break;
            }
            case SELECT: {
                if (this.method.returnsVoid() && this.method.hasResultHandler()) {
                    executeWithResultHandler(sqlSession, args);
                    result = null;
                } else if (this.method.returnsMany()) {
                    // 分页查询处理
                    if (args != null && (Pageable.class.isAssignableFrom(this.method.getReturnType()) || ClassUtil.isAssignableFrom(Pageable.class, args))) {
                        result = executeForPageable(sqlSession, args);
                    } else {
                        result = executeForMany(sqlSession, args);
                    }
                } else if (this.method.returnsMap()) {
                    result = executeForMap(sqlSession, args);
                } else if (this.method.returnsCursor()) {
                    result = executeForCursor(sqlSession, args);
                } else {
                    Object param = this.method.convertArgsToSqlCommandParam(args);
                    if (args != null && (Pageable.class.isAssignableFrom(this.method.getReturnType()) || ClassUtil.isAssignableFrom(Pageable.class, args))) {
                        // 分页查询处理
                        result = executeForPageable(sqlSession, args);
                    } else {
                        result = sqlSession.selectOne(this.command.getName(), param);
                        if (this.method.returnsOptional() && (result == null || !this.method.getReturnType().equals(result.getClass()))) {
                            result = Optional.ofNullable(result);
                        }
                    }
                }
                break;
            }
            case FLUSH:
                result = sqlSession.flushStatements();
                break;
            default:
                throw new BindingException("Unknown execution method for: " + this.command.getName());
        }
        if (result == null && this.method.getReturnType().isPrimitive() && !this.method.returnsVoid()) {
            throw new BindingException("Mapper method `" + this.command.getName() + "` attempted to return null from a method with a primitive type (" + this.method.getReturnType() + ").");
        }
        return result;
    }

    private Object rowCountResult(int rowCount) {
        final Object result;
        if (this.method.returnsVoid()) {
            result = null;
        } else if (Integer.class.equals(this.method.getReturnType()) || Integer.TYPE.equals(this.method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(this.method.getReturnType()) || Long.TYPE.equals(this.method.getReturnType())) {
            result = (long) rowCount;
        } else if (Boolean.class.equals(this.method.getReturnType()) || Boolean.TYPE.equals(this.method.getReturnType())) {
            result = rowCount > 0;
        } else {
            throw new BindingException("Mapper method `" + this.command.getName() + "` has an unsupported return type: " + this.method.getReturnType());
        }
        return result;
    }

    private void executeWithResultHandler(SqlSession sqlSession, Object[] args) {
        MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(this.command.getName());
        if (!StatementType.CALLABLE.equals(ms.getStatementType()) &&
                void.class.equals(ms.getResultMaps().get(0).getType())) {
            throw new BindingException("Method `" + this.command.getName() + "` needs aither a @ResultMap annotation, a @ResultType annotation," +
                    " or a resultType attribute in XML so ResultHandler can be used as a parameter.");
        }
        Object param = this.method.convertArgsToSqlCommandParam(args);
        if (this.method.hasRowBounds()) {
            RowBounds rowBounds = this.method.extractRowBounds(args);
            sqlSession.select(this.command.getName(), param, rowBounds, this.method.extractResultHandler(args));
        } else {
            sqlSession.select(this.command.getName(), param, this.method.extractResultHandler(args));
        }
    }

    private <E> Object executeForMany(SqlSession sqlSession, Object[] args) {
        List<E> result = getResult(sqlSession, args);
        // issue #510 Collections & arrays support
        if (!this.method.getReturnType().isAssignableFrom(result.getClass())) {
            if (method.getReturnType().isArray()) {
                return convertToArray(result);
            } else {
                return convertToDeclaredCollection(sqlSession.getConfiguration(), result);
            }
        }
        return result;
    }

    private <E> List<E> getResult(SqlSession sqlSession, Object[] args) {
        Object param = this.method.convertArgsToSqlCommandParam(args);
        if (this.method.hasRowBounds()) {
            RowBounds rowBounds = this.method.extractRowBounds(args);
            return sqlSession.selectList(this.command.getName(), param, rowBounds);
        } else {
            return sqlSession.selectList(this.command.getName(), param);
        }
    }

    @SuppressWarnings("unchecked")
    private <E> Object convertToArray(List<E> list) {
        Class<?> arrayComponentType = this.method.getReturnType().getComponentType();
        Object array = Array.newInstance(arrayComponentType, list.size());
        // 检查是否为基本数据类
        if (arrayComponentType.isPrimitive()) {
            int i = 0;
            int size = list.size();
            for (; i < size; i++) {
                Array.set(array, i, list.get(i));
            }
            return array;
        } else {
            return list.toArray((E[]) array);
        }
    }

    private <E> Object convertToDeclaredCollection(Configuration configuration, List<E> list) {
        Object collection = configuration.getObjectFactory().create(this.method.getReturnType());
        MetaObject metaObject = configuration.newMetaObject(collection);
        metaObject.addAll(list);
        return collection;
    }

    private <K, V> Map<K, V> executeForMap(SqlSession sqlSession, Object[] args) {
        Map<K, V> result;
        Object param = this.method.convertArgsToSqlCommandParam(args);
        if (this.method.hasRowBounds()) {
            RowBounds rowBounds = this.method.extractRowBounds(args);
            result = sqlSession.selectMap(this.command.getName(), param, this.method.getMapKey(), rowBounds);
        } else {
            result = sqlSession.selectMap(this.command.getName(), param, this.method.getMapKey());
        }
        return result;
    }

    private <T> Cursor<T> executeForCursor(SqlSession sqlSession, Object[] args) {
        Cursor<T> result;
        Object param = this.method.convertArgsToSqlCommandParam(args);
        if (this.method.hasRowBounds()) {
            RowBounds rowBounds = this.method.extractRowBounds(args);
            result = sqlSession.selectCursor(this.command.getName(), param, rowBounds);
        } else {
            result = sqlSession.selectCursor(this.command.getName(), param);
        }
        return result;
    }

    private <E> Object executeForPageable(SqlSession sqlSession, Object[] args) {
        return getResult(sqlSession, args);
    }

    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = 1539481317950740331L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }
    }

    /**
     * SQL指令
     */
    public static class SqlCommand {

        /**
         * 名称
         */
        private final String name;
        /**
         * 类型
         */
        private final SqlCommandType type;

        /**
         * 构造方法
         * @param configuration   配置
         * @param mapperInterface 接口
         * @param method          方法
         */
        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            final String methodName = method.getName();
            final Class<?> declaringClass = method.getDeclaringClass();
            MappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass, configuration);
            if (ms == null) {
                if (method.getAnnotation(Flush.class) != null) {
                    this.name = null;
                    this.type = SqlCommandType.FLUSH;
                } else {
                    throw new BindingException("Invalid bound statement (not found): " + mapperInterface.getName() + "." + methodName);
                }
            } else {
                this.name = ms.getId();
                this.type = ms.getSqlCommandType();
                if (type == SqlCommandType.UNKNOWN) {
                    throw new BindingException("Unknown execution method for: " + name);
                }
            }
        }

        private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
                                                       Class<?> declaringClass, Configuration configuration) {
            String statementId = mapperInterface.getName() + "." + methodName;
            if (configuration.hasStatement(statementId)) {
                return configuration.getMappedStatement(statementId);
            } else if (mapperInterface.equals(declaringClass)) {
                return null;
            }
            for (Class<?> superInterface : mapperInterface.getInterfaces()) {
                if (declaringClass.isAssignableFrom(superInterface)) {
                    MappedStatement ms = resolveMappedStatement(superInterface, methodName, declaringClass, configuration);
                    if (ms != null) {
                        return ms;
                    }
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }

    public static class MethodSignature {

        private final boolean returnsMany;
        private final boolean returnsMap;
        private final boolean returnsVoid;
        private final boolean returnsCursor;
        private final boolean returnsOptional;
        private final Class<?> returnType;
        private final String mapKey;
        private final Integer resultHandlerIndex;
        private final Integer rowBoundsIndex;
        private final ParamNameResolver paramNameResolver;

        public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
            Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
            if (resolvedReturnType instanceof Class<?>) {
                this.returnType = (Class<?>) resolvedReturnType;
            } else if (resolvedReturnType instanceof ParameterizedType) {
                this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
            } else {
                this.returnType = method.getReturnType();
            }
            this.returnsVoid = Void.class.equals(this.returnType);
            this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
            this.returnsCursor = Cursor.class.equals(this.returnType);
            this.returnsOptional = Optional.class.equals(this.returnType);
            this.mapKey = getMapKey(method);
            this.returnsMap = this.mapKey != null;
            this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
            this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
            this.paramNameResolver = new ParamNameResolver(configuration, method);
        }

        public Object convertArgsToSqlCommandParam(Object[] args) {
            return this.paramNameResolver.getNamedParams(args);
        }

        public boolean hasRowBounds() {
            return this.rowBoundsIndex != null;
        }

        public RowBounds extractRowBounds(Object[] args) {
            return this.hasRowBounds() ? (RowBounds) args[this.rowBoundsIndex] : null;
        }

        public boolean hasResultHandler() {
            return this.resultHandlerIndex != null;
        }

        public ResultHandler<?> extractResultHandler(Object[] args) {
            return this.hasResultHandler() ? (ResultHandler<?>) args[this.resultHandlerIndex] : null;
        }

        public boolean returnsMany() {
            return returnsMany;
        }

        public boolean returnsMap() {
            return returnsMap;
        }

        public boolean returnsVoid() {
            return returnsVoid;
        }

        public boolean returnsCursor() {
            return returnsCursor;
        }

        public boolean returnsOptional() {
            return returnsOptional;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public String getMapKey() {
            return mapKey;
        }

        private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
            Integer index = null;
            final Class<?>[] argTypes = method.getParameterTypes();
            int i = 0;
            int size = argTypes.length;
            for (; i < size; i++) {
                if (paramType.isAssignableFrom(argTypes[i])) {
                    if (index == null) {
                        index = i;
                    } else {
                        throw new BindingException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters ");
                    }
                }
            }
            return index;
        }

        private String getMapKey(Method method) {
            String mapKey = null;
            if (Map.class.isAssignableFrom(method.getReturnType())) {
                final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
                if (mapKeyAnnotation != null) {
                    mapKey = mapKeyAnnotation.value();
                }
            }
            return mapKey;
        }
    }
}
