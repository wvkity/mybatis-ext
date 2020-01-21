package com.wkit.lost.mybatis.starter.example.config;

import com.wkit.lost.mybatis.data.auditing.AuditorAware;

public class DefaultAuditorAware implements AuditorAware {
    
    @Override
    public Object currentUserName() {
        return "admin";
    }

    @Override
    public Object currentUserId() {
        return 1L;
    }
}
