package com.wkit.lost.mybatis.starter.example.junit.service;

import com.wkit.lost.mybatis.starter.example.MyBatisStarterApplicationRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith( SpringExtension.class )
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { MyBatisStarterApplicationRunner.class } )
public class RootTestRunner {
}
