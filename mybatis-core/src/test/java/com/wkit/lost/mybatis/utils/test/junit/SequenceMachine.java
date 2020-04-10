package com.wkit.lost.mybatis.utils.test.junit;

import com.wkit.lost.mybatis.core.snowflake.sequence.Sequence;

import java.util.List;
import java.util.Set;

public class SequenceMachine implements Runnable {

    private Sequence sequence;
    private Set<Long> sequenceCache;
    private List<String> repeatCache;

    public SequenceMachine(Sequence sequence, Set<Long> sequenceCache, List<String> repeatCache) {
        this.sequence = sequence;
        this.sequenceCache = sequenceCache;
        this.repeatCache = repeatCache;
    }

    @Override
    public void run() {

    }
}
