package com.wkit.lost.mybatis.spring.boot.worker;

import com.wkit.lost.mybatis.snowflake.sequence.Level;
import com.wkit.lost.mybatis.snowflake.sequence.Mode;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * WorkerSequence配置类
 * @author wvkity
 */
@Data
@Accessors( chain = true )
@Configuration
@ConfigurationProperties( prefix = "lost.sequence", ignoreInvalidFields = true )
public class SequenceAutoConfiguration {
    /**
     * 开始时间(默认为2019-12-12 12:12:12)
     */
    private long epochTimestamp = -1L;

    /**
     * 机器ID
     */
    private long workerId = 0L;

    /**
     * 注册中心ID
     */
    private long dataCenterId = 0L;

    /**
     * 级别
     */
    private Level level = Level.UNKNOWN;

    /**
     * 模式
     */
    private Mode mode = Mode.UNKNOWN;
}
