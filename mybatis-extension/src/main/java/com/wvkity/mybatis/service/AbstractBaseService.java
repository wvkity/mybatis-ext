package com.wvkity.mybatis.service;

import com.wkit.lost.paging.Pageable;
import com.wvkity.mybatis.batch.BatchDataBeanWrapper;
import com.wvkity.mybatis.binding.MyBatisMapperMethod;
import com.wvkity.mybatis.core.handler.TableHandler;
import com.wvkity.mybatis.core.wrapper.criteria.AbstractQueryCriteriaWrapper;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.exception.MyBatisException;
import com.wvkity.mybatis.mapper.BaseMapper;
import com.wvkity.mybatis.session.SqlSessionUtil;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.ClassUtil;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 通用Service抽象类
 * @param <Mapper> Mapper接口
 * @param <T>      实体类
 * @param <V>      返回值类型
 * @param <PK>     主键类型
 * @author wvkity
 */
public abstract class AbstractBaseService<Mapper extends BaseMapper<T, V, PK>, T, V, PK>
        implements BaseService<T, V, PK> {

    protected static final String METHOD_INSERT = "insert";
    protected static final String METHOD_INSERT_NOT_WITH_NULL = "insertNotWithNull";
    protected final ArrayList<V> EMPTY_DATA = new ArrayList<>(0);

    @Inject
    protected Mapper mapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int save(T entity) {
        return this.mapper.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveNotWithNull(T entity) {
        return this.mapper.insertNotWithNull(entity);
    }

    @SuppressWarnings({"unchecked"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSave(T... entities) {
        return batchSave(ArrayUtil.toList(entities), DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSave(Collection<T> entities) {
        return batchSave(entities, DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSave(Collection<T> entities, int batchSize) {
        return batchSave(BatchDataBeanWrapper.wrap(entities, batchSize));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSave(BatchDataBeanWrapper<T> wrapper) {
        return mapper.batchInsert(wrapper);
    }

    @SuppressWarnings({"unchecked"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveNotWithAudit(T... entities) {
        return batchSaveNotWithAudit(ArrayUtil.toList(entities), DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveNotWithAudit(Collection<T> entities) {
        return batchSaveNotWithAudit(entities, DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveNotWithAudit(Collection<T> entities, int batchSize) {
        return batchSaveNotWithAudit(BatchDataBeanWrapper.wrap(entities, batchSize));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveNotWithAudit(BatchDataBeanWrapper<T> wrapper) {
        return mapper.batchInsertNotWithAudit(wrapper);
    }

    @SuppressWarnings({"unchecked"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int embeddedBatchSave(T... entities) {
        return embeddedBatchSave(ArrayUtil.toList(entities), DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int embeddedBatchSave(Collection<T> entities) {
        return embeddedBatchSave(entities, DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int embeddedBatchSave(Collection<T> entities, int batchSize) {
        return execBatchMethod(entities, batchSize, METHOD_INSERT);
    }

    @SuppressWarnings({"unchecked"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int embeddedBatchSaveNotWithNull(T... entities) {
        return embeddedBatchSaveNotWithNull(ArrayUtil.toList(entities), DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int embeddedBatchSaveNotWithNull(Collection<T> entities) {
        return embeddedBatchSaveNotWithNull(entities, DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int embeddedBatchSaveNotWithNull(Collection<T> entities, int batchSize) {
        return execBatchMethod(entities, batchSize, METHOD_INSERT_NOT_WITH_NULL);
    }

    private int execBatchMethod(Collection<T> entities, int batchSize, String method) {
        if (CollectionUtil.isEmpty(entities)) {
            return 0;
        }
        SqlSession session = batchSession();
        int i = 0;
        String statement = sqlStatement(method);
        Map<String, Object> param = new MyBatisMapperMethod.ParamMap<>();
        for (T entity : entities) {
            param.put(Constants.PARAM_ENTITY, entity);
            session.insert(statement, param);
            if (i >= 1 && i % batchSize == 0) {
                session.flushStatements();
            }
            i++;
        }
        session.flushStatements();
        return i;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(T entity) {
        if (entity == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.update(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(Criteria<T> criteria) {
        if (criteria == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.updateByCriteria(criteria.as(false));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateNotWithNull(T entity) {
        if (entity == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.updateNotWithNull(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateNotWithNull(T entity, Criteria<T> criteria) {
        if (entity == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.mixinUpdateNotWithNull(entity, criteria.as(false));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateNotWithLocking(T entity) {
        if (entity == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.updateNotWithLocking(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateNotWithNullAndLocking(T entity) {
        if (entity == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.updateNotWithNullAndLocking(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(T entity) {
        if (entity == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.delete(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(PK id) {
        if (id == null) {
            throw new MyBatisException("The primary key parameter cannot be empty");
        }
        return mapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(Criteria<T> criteria) {
        if (criteria == null) {
            throw new MyBatisException("The specified criteria parameter cannot be null");
        }
        return mapper.deleteByCriteria(criteria.as(false));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int logicDelete(T entity) {
        if (entity == null) {
            throw new MyBatisException("The specified object parameter cannot be null");
        }
        return mapper.logicDelete(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int logicDelete(Criteria<T> criteria) {
        if (criteria == null) {
            throw new MyBatisException("The specified criteria parameter cannot be null");
        }
        return mapper.logicDeleteByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelete(T... entities) {
        return batchDeleteByEntities(Arrays.asList(entities));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDeleteByEntities(Collection<T> entities) {
        if (entities == null) {
            throw new MyBatisException("The entity object collection parameter cannot be empty.");
        }
        List<T> list = entities.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        if (CollectionUtil.isEmpty(list)) {
            throw new MyBatisException("The entity object collection parameter cannot be empty.");
        }
        return mapper.batchDelete(list);
    }

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDeleteById(PK... idArray) {
        return batchDeleteById(ArrayUtil.toList(idArray));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDeleteById(Collection<PK> idList) {
        if (idList == null) {
            throw new MyBatisException("Primary key set parameters cannot be empty");
        }
        List<PK> list = idList.stream().filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
        if (CollectionUtil.isEmpty(list)) {
            throw new MyBatisException("Primary key set parameters cannot be empty");
        }
        return mapper.batchDeleteById(list);
    }

    @Override
    public boolean exists(T entity) {
        return entity != null && mapper.exists(entity) > 0;
    }

    @Override
    public boolean exists(Criteria<T> criteria) {
        return criteria != null && mapper.existsByCriteria(criteria) > 0;
    }

    @Override
    public boolean existsById(PK id) {
        return id != null && this.mapper.existsById(id) > 0;
    }

    @Override
    public long count(T entity) {
        return entity == null ? 0 : mapper.count(entity);
    }

    @Override
    public long count(Criteria<T> criteria) {
        return criteria == null ? 0 : mapper.countByCriteria(criteria);
    }

    @Override
    public Optional<V> selectOne(PK id) {
        return id == null ? Optional.empty() : mapper.selectOne(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<V> list(PK... ids) {
        return list(Arrays.asList(ids));
    }

    public List<V> list(Collection<PK> idList) {
        List<PK> pks = Optional.ofNullable(idList)
                .orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return CollectionUtil.isEmpty(pks) ? EMPTY_DATA : mapper.list(pks);
    }

    @Override
    public List<V> list(T entity) {
        return entity == null ? EMPTY_DATA : mapper.listByEntity(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<V> listByEntities(T... entities) {
        return list(Arrays.asList(entities));
    }

    @Override
    public List<V> list(List<T> entities) {
        List<T> list = Optional.ofNullable(entities)
                .orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return CollectionUtil.isEmpty(list) ? EMPTY_DATA : mapper.listByEntities(list);
    }

    @Override
    public List<V> list(Criteria<T> criteria) {
        return criteria == null ? EMPTY_DATA : mapper.listByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> customize(Criteria<T> criteria) {
        if (criteria instanceof AbstractQueryCriteriaWrapper) {
            AbstractQueryCriteriaWrapper<T> it = (AbstractQueryCriteriaWrapper<T>) criteria;
            if (it.resultType() != null || StringUtil.hasText(it.resultMap())) {
                List<Object> result = mapper.objectList(criteria);
                return Optional.ofNullable(result).map(value -> (List<E>) value).orElse(new ArrayList<>(0));
            }
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<Object> objects(Criteria<T> criteria) {
        if (criteria == null) {
            return new ArrayList<>(0);
        }
        return mapper.objectList(criteria);
    }

    @Override
    public List<Object[]> array(Criteria<T> criteria) {
        if (criteria == null) {
            return new ArrayList<>(0);
        }
        return mapper.arrayList(criteria);
    }

    @Override
    public List<Map<String, Object>> map(Criteria<T> criteria) {
        if (criteria == null) {
            return new ArrayList<>(0);
        }
        return mapper.mapList(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> customize(Criteria<T> criteria, Pageable pageable) {
        if (criteria instanceof AbstractQueryCriteriaWrapper) {
            AbstractQueryCriteriaWrapper<T> it = (AbstractQueryCriteriaWrapper<T>) criteria;
            if (it.resultType() != null || StringUtil.hasText(it.resultMap())) {
                List<Object> result = mapper.objectPageableList(criteria, pageable);
                return Optional.ofNullable(result).map(value -> (List<E>) value).orElse(new ArrayList<>(0));
            }
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<Object> objects(Criteria<T> criteria, Pageable pageable) {
        if (criteria == null) {
            return new ArrayList<>(0);
        }
        return mapper.objectPageableList(criteria, pageable);
    }

    @Override
    public List<Object[]> array(Criteria<T> criteria, Pageable pageable) {
        if (criteria == null) {
            return new ArrayList<>(0);
        }
        return mapper.arrayPageableList(criteria, pageable);
    }

    @Override
    public List<Map<String, Object>> map(Criteria<T> criteria, Pageable pageable) {
        if (criteria == null) {
            return new ArrayList<>(0);
        }
        return mapper.mapPageableList(criteria, pageable);
    }

    @Override
    public List<V> list(Pageable pageable) {
        return pageable == null ? EMPTY_DATA : mapper.simpleList(pageable);
    }

    @Override
    public List<V> list(T entity, Pageable pageable) {
        return entity == null ? EMPTY_DATA : mapper.pageableList(entity, pageable);
    }

    @Override
    public List<V> list(Criteria<T> criteria, Pageable pageable) {
        if (criteria == null) {
            return EMPTY_DATA;
        }
        return mapper.pageableListByCriteria(criteria, pageable);
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    /**
     * 获取SQL statement
     * @param method 方法名
     * @return sql statement
     */
    protected String sqlStatement(String method) {
        return TableHandler.getTable(getEntityClass()).getSqlStatement(method);
    }

    /**
     * 获取实体类
     * @return {@link Class}
     */
    @SuppressWarnings({"unchecked"})
    protected Class<T> getEntityClass() {
        return (Class<T>) ClassUtil.getGenericType(getClass(), 1);
    }

    /**
     * 获取批量操作{@link SqlSession}
     * @return {@link SqlSession}
     */
    protected SqlSession batchSession() {
        return SqlSessionUtil.batchSession(getEntityClass());
    }

    /**
     * 释放批量操作{@link SqlSession}
     * @param session {@link SqlSession}
     */
    protected void closeSession(SqlSession session) {
        SqlSessionUtil.closeSession(session, getEntityClass());
    }
}
