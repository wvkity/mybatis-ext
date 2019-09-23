package com.wkit.lost.mybatis.snowflake.clock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MillisecondsClock {

    /**
     * 频率
     */
    private final long period;
    private volatile long now;

    private static final class ClockInstance {
        protected static final MillisecondsClock INSTANCE = new MillisecondsClock();
    }

    private MillisecondsClock() {
        this.period = 1L;
        this.now = System.currentTimeMillis();
        start();
    }

    private void start() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor( r -> {
            Thread thread = new Thread( r, "MILLISECOND CLOCK" );
            thread.setDaemon( true );
            return thread;
        } );
        // 固定每1ms获取一次系统日期
        exec.scheduleAtFixedRate( () -> now = System.currentTimeMillis(), period,
                period, TimeUnit.MILLISECONDS );
    }

    public static MillisecondsClock getInstance() {
        return ClockInstance.INSTANCE;
    }

    public static long currentTimeMillis() {
        return getInstance().now;
    }
}
