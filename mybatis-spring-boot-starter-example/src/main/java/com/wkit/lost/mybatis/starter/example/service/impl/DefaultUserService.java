package com.wkit.lost.mybatis.starter.example.service.impl;

import com.wkit.lost.mybatis.service.AbstractServiceExecutor;
import com.wkit.lost.mybatis.starter.example.entity.Grade;
import com.wkit.lost.mybatis.starter.example.entity.User;
import com.wkit.lost.mybatis.starter.example.mapper.UserMapper;
import com.wkit.lost.mybatis.starter.example.service.GradeService;
import com.wkit.lost.mybatis.starter.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultUserService extends AbstractServiceExecutor<UserMapper, User> implements UserService {

    private final GradeService gradeService;

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int testSave( Grade grade, List<User> users ) {
        gradeService.save( grade );
        return embeddedBatchSave( users );
    }
}
