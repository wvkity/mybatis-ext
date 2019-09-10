package com.wkit.lost.mybatis.executor.resultset;

import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.ObjectTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@inheritDoc}
 */
public class MyBatisResultSetWrapper extends ResultSetWrapper {

    private final ResultSet resultSet;
    private final TypeHandlerRegistry typeHandlerRegistry;
    private final List<String> columnNames = new ArrayList<>();
    private final List<String> classNames = new ArrayList<>();
    private final List<JdbcType> jdbcTypes = new ArrayList<>();
    private final Map<String, Map<Class<?>, TypeHandler<?>>> typeHandlerMap = new HashMap<>();
    private final Map<String, List<String>> mappedColumnNamesMap = new HashMap<>();
    private final Map<String, List<String>> unMappedColumnNamesMap = new HashMap<>();
    //
    private final List<String> simpleColumnNames = new ArrayList<>();
    private final Map<String, String> simpleMappedCache = new HashMap<>();

    /**
     * 构造方法
     * @param rs            {@link ResultSet}
     * @param configuration {@link Configuration}
     * @throws SQLException \n
     */
    public MyBatisResultSetWrapper( ResultSet rs, Configuration configuration ) throws SQLException {
        super( rs, configuration );
        this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        this.resultSet = rs;
        final ResultSetMetaData metaData = rs.getMetaData();
        final int columnCount = metaData.getColumnCount();
        AtomicInteger sameNameIndex = new AtomicInteger( 1 );
        for ( int i = 1; i <= columnCount; i++ ) {
            String columnName = configuration.isUseColumnLabel() ? metaData.getColumnLabel( i ) : metaData.getColumnName( i );
            // 替换存在()的列名
            columnNames.add( columnName );
            jdbcTypes.add( JdbcType.forCode( metaData.getColumnType( i ) ) );
            classNames.add( metaData.getColumnClassName( i ) );
            // 检查是否包含括号
            boolean hasBracket = columnName.contains( "(" );
            if ( hasBracket ) {
                String simpleColumnName = columnName.replaceAll( "\\(.*\\)", "" );
                // 检查是否存在同名
                if ( simpleColumnNames.contains( simpleColumnName ) ) {
                    simpleColumnName = simpleColumnName + "_" + sameNameIndex.incrementAndGet();
                }
                simpleColumnNames.add( simpleColumnName );
                simpleMappedCache.put( columnName, simpleColumnName );
            } else {
                simpleColumnNames.add( columnName );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getColumnNames() {
        return this.columnNames;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getClassNames() {
        return Collections.unmodifiableList( classNames );
    }

    public List<JdbcType> getJdbcTypes() {
        return jdbcTypes;
    }

    /**
     * {@inheritDoc}
     */
    public JdbcType getJdbcType( String columnName ) {
        for ( int i = 0; i < columnNames.size(); i++ ) {
            if ( columnNames.get( i ).equalsIgnoreCase( columnName ) ) {
                return jdbcTypes.get( i );
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public TypeHandler<?> getTypeHandler( Class<?> propertyType, String columnName ) {
        TypeHandler<?> handler = null;
        Map<Class<?>, TypeHandler<?>> columnHandlers = typeHandlerMap.get( columnName );
        if ( columnHandlers == null ) {
            columnHandlers = new HashMap<>();
            typeHandlerMap.put( columnName, columnHandlers );
        } else {
            handler = columnHandlers.get( propertyType );
        }
        if ( handler == null ) {
            JdbcType jdbcType = getJdbcType( columnName );
            handler = typeHandlerRegistry.getTypeHandler( propertyType, jdbcType );
            // Replicate logic of UnknownTypeHandler#resolveTypeHandler
            // See issue #59 comment 10
            if ( handler == null || handler instanceof UnknownTypeHandler ) {
                final int index = columnNames.indexOf( columnName );
                final Class<?> javaType = resolveClass( classNames.get( index ) );
                if ( javaType != null && jdbcType != null ) {
                    handler = typeHandlerRegistry.getTypeHandler( javaType, jdbcType );
                } else if ( javaType != null ) {
                    handler = typeHandlerRegistry.getTypeHandler( javaType );
                } else if ( jdbcType != null ) {
                    handler = typeHandlerRegistry.getTypeHandler( jdbcType );
                }
            }
            if ( handler == null || handler instanceof UnknownTypeHandler ) {
                handler = new ObjectTypeHandler();
            }
            columnHandlers.put( propertyType, handler );
        }
        return handler;
    }

    private Class<?> resolveClass( String className ) {
        try {
            // #699 className could be null
            if ( className != null ) {
                return Resources.classForName( className );
            }
        } catch ( ClassNotFoundException e ) {
            // ignore
        }
        return null;
    }

    private void loadMappedAndUnmappedColumnNames( ResultMap resultMap, String columnPrefix ) throws SQLException {
        List<String> mappedColumnNames = new ArrayList<>();
        List<String> unmappedColumnNames = new ArrayList<>();
        final String upperColumnPrefix = columnPrefix == null ? null : columnPrefix.toUpperCase( Locale.ENGLISH );
        final Set<String> mappedColumns = prependPrefixes( resultMap.getMappedColumns(), upperColumnPrefix );
        for ( String columnName : columnNames ) {
            final String upperColumnName = columnName.toUpperCase( Locale.ENGLISH );
            if ( mappedColumns.contains( upperColumnName ) ) {
                mappedColumnNames.add( upperColumnName );
            } else {
                unmappedColumnNames.add( columnName );
            }
        }
        mappedColumnNamesMap.put( getMapKey( resultMap, columnPrefix ), mappedColumnNames );
        unMappedColumnNamesMap.put( getMapKey( resultMap, columnPrefix ), unmappedColumnNames );
    }

    public List<String> getMappedColumnNames( ResultMap resultMap, String columnPrefix ) throws SQLException {
        List<String> mappedColumnNames = mappedColumnNamesMap.get( getMapKey( resultMap, columnPrefix ) );
        if ( mappedColumnNames == null ) {
            loadMappedAndUnmappedColumnNames( resultMap, columnPrefix );
            mappedColumnNames = mappedColumnNamesMap.get( getMapKey( resultMap, columnPrefix ) );
        }
        return mappedColumnNames;
    }

    public List<String> getUnmappedColumnNames( ResultMap resultMap, String columnPrefix ) throws SQLException {
        List<String> unMappedColumnNames = unMappedColumnNamesMap.get( getMapKey( resultMap, columnPrefix ) );
        if ( unMappedColumnNames == null ) {
            loadMappedAndUnmappedColumnNames( resultMap, columnPrefix );
            unMappedColumnNames = unMappedColumnNamesMap.get( getMapKey( resultMap, columnPrefix ) );
        }
        return unMappedColumnNames;
    }

    private String getMapKey( ResultMap resultMap, String columnPrefix ) {
        return resultMap.getId() + ":" + columnPrefix;
    }

    private Set<String> prependPrefixes( Set<String> columnNames, String prefix ) {
        if ( columnNames == null || columnNames.isEmpty() || prefix == null || prefix.length() == 0 ) {
            return columnNames;
        }
        final Set<String> prefixed = new HashSet<>();
        for ( String columnName : columnNames ) {
            prefixed.add( prefix + columnName );
        }
        return prefixed;
    }

    public List<String> getSimpleColumnNames() {
        return simpleColumnNames;
    }

    public Map<String, String> getSimpleMappedCache() {
        return simpleMappedCache;
    }
}
