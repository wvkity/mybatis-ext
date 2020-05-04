package com.wvkity.mybatis.utils.test.junit;

import com.wvkity.mybatis.core.snowflake.clock.MillisecondsClock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MillisecondClockTest implements Runnable {

    static final CountDownLatch LATCH = new CountDownLatch(4000);
    static final AtomicInteger INDEX = new AtomicInteger();
    static final MillisecondClockTest CLOCK = new MillisecondClockTest();

    @Override
    public void run() {
        try {
            System.out.println("index: " + INDEX.incrementAndGet());
            System.out.println("system: " + System.currentTimeMillis());
            System.out.println("custom: " + MillisecondsClock.currentTimeMillis());
            System.out.println("===================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ExecutorService exec = Executors.newFixedThreadPool(4000);
            for (int i = 0; i < 4000; i++) {
                exec.submit(CLOCK);
            }
            LATCH.await();
            System.out.println("Fire!");
            exec.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
