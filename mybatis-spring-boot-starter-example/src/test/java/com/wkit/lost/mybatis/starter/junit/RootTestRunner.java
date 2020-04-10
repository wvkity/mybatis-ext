package com.wkit.lost.mybatis.starter.junit;

import com.wkit.lost.mybatis.starter.example.MyBatisStarterApplicationRunner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MyBatisStarterApplicationRunner.class})
public class RootTestRunner {

    public static final String DEF_SYS_USER_NAME = "System";
    public static final Long DEF_SYS_USER_ID = 1L;
}
