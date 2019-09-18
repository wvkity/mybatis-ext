package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceSameExecutor;
import com.wkit.lost.mybatis.starter.example.beans.UserVisitRecord;
import com.wkit.lost.mybatis.starter.example.mapper.UserVisitRecordMapper;
import com.wkit.lost.mybatis.starter.example.service.UserVisitRecordService;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserVisitRecordService extends AbstractServiceSameExecutor<UserVisitRecordMapper, UserVisitRecord>
        implements UserVisitRecordService {
}
