package com.wvkity.mybatis.starter.example.config;

import com.wvkity.mybatis.core.data.auditing.AuditorAware;

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
