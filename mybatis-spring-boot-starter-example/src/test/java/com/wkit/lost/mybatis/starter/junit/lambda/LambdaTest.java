package com.wkit.lost.mybatis.starter.junit.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.function.Function;

public class LambdaTest {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LambdaApp implements Serializable {

        private static final long serialVersionUID = -8071498631838151915L;
        private Long id;
        private String userName;
        private LocalDateTime createdTime;
    }
    
    private static interface Lambda<T> {
        <R> void t1(Function<T, R> function, R object);
    }
    
    private static class LambdaImpl implements Lambda<LambdaApp> {
        @Override
        public <R> void t1(Function<LambdaApp, R> function, R object) {
            System.out.println(object);
        }
    }

    public static void main(String[] args) {
        LambdaApp app = new LambdaApp();
        Lambda<LambdaApp> lambda = new LambdaImpl();
        lambda.t1(LambdaApp::getUserName, "user");
    }
}
