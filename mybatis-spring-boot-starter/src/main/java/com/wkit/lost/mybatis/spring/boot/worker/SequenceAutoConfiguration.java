package com.wkit.lost.mybatis.spring.boot.worker;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * WorkerSequence配置类
 * @author DT
 */
@Data
@Accessors( chain = true )
@Configuration
@ConfigurationProperties( prefix = "sequence", ignoreInvalidFields = true )
public class SequenceAutoConfiguration {
    /**
     * 开始时间(默认为2019-01-01)
     */
    private long epochTimestamp = -1L;

    /**
     * workerId
     */
    private long workerId = 0L;

    /**
     * 注册中心ID
     */
    private long dataCenterId = 0L;

    /**
     * 是否启用秒级别
     */
    private boolean secondEnable = false;

    /**
     * 使用默认sequence(即根据mac地址自动分配)
     */
    private boolean macEnable = false;
}
