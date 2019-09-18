package com.wkit.lost.mybatis.starter.example.mapper;

import com.wkit.lost.mybatis.mapper.MapperSameExecutor;
import com.wkit.lost.mybatis.starter.example.beans.UserVisitRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVisitRecordMapper extends MapperSameExecutor<UserVisitRecord> {
}
