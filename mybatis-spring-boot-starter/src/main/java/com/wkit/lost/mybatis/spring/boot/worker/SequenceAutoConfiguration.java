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
     * 级别
     */
    private Level level = Level.MILLISECOND;

    /**
     * 模式
     */
    private Mode mode = Mode.SPECIFIED;
}
