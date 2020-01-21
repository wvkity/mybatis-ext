package com.wkit.lost.mybatis.spring.boot.sequence;

import com.wkit.lost.mybatis.snowflake.sequence.Level;
import com.wkit.lost.mybatis.snowflake.sequence.Rule;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WorkerSequence配置类
 * @author wvkity
 */
@Data
@Accessors( chain = true )
@ConfigurationProperties( prefix = "lost.sequence", ignoreInvalidFields = true )
public class SequenceProperties {
    
    /**
     * 开始时间(默认为2019-12-12 12:12:12)
     */
    private long epochTimestamp = -1L;

    /**
     * 机器ID
     */
    private long workerId = 1L;

    /**
     * 注册中心ID
     */
    private long dataCenterId = 1L;

    /**
     * 级别
     */
    private Level level = Level.MILLISECONDS;

    /**
     * 规则
     */
    private Rule rule = Rule.SPECIFIED;
}
