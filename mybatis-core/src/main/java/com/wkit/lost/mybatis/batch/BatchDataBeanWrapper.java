package com.wkit.lost.mybatis.batch;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 批量数据包装类
 * @param <T> 实体类型
 * @author wvkity
 */
public class BatchDataBeanWrapper<T> {

    private static final int DEFAULT_BATCH_SIZE = 500;

    /**
     * 待处理数据
     */
    @Getter
    private final Collection<T> data;

    /**
     * 批量大小
     */
    @Getter
    private final int batchSize;
    private final List<Integer> affectedRowCounts;

    private BatchDataBeanWrapper(Collection<T> data, int batchSize) {
        this.data = data;
        this.batchSize = batchSize;
        this.affectedRowCounts = new ArrayList<>(data.size());
    }

    public void addRowCounts(int[] counts) {
        for (int i : counts) {
            affectedRowCounts.add(i);
        }
    }

    public int getAffectedRowCount() {
        int count = 0;
        for (Integer i : affectedRowCounts) {
            if (i > 0) {
                count += 1;
            }
        }
        return count;
    }

    public static <T> BatchDataBeanWrapper<T> wrap(Collection<T> data) {
        return wrap(data, DEFAULT_BATCH_SIZE);
    }

    public static <T> BatchDataBeanWrapper<T> wrap(Collection<T> data, int batchSize) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (batchSize < 1) {
            throw new IllegalArgumentException("Batch size cannot be less than 1");
        }
        return new BatchDataBeanWrapper<>(data, batchSize);
    }
}
