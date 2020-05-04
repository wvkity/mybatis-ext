package com.wvkity.mybatis.starter.example.service.impl;

import com.wvkity.mybatis.service.AbstractSimpleService;
import com.wvkity.mybatis.starter.example.entity.Klass;
import com.wvkity.mybatis.starter.example.mapper.KlassMapper;
import com.wvkity.mybatis.starter.example.service.KlassService;
import org.springframework.stereotype.Service;

@Service
public class DefaultKlassService extends AbstractSimpleService<KlassMapper, Klass> implements KlassService {
}
