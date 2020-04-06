package com.wkit.lost.mybatis.starter.example.config;

import com.wkit.lost.mybatis.core.data.auditing.AuditorAware;

public class DefaultAuditorAware implements AuditorAware {

    @Override
    public Object currentUserName() {
        return "root";
    }

    @Override
    public Object currentUserId() {
        return 2L;
    }
}
