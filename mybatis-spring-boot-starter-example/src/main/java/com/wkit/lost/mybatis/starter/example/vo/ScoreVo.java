package com.wkit.lost.mybatis.starter.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreVo implements Serializable {
    private static final long serialVersionUID = 9130939089675523699L;

    private Integer state;
    private Integer sex;
    private Integer count;
    private Integer min;
    private Integer max;
    private Integer sum;
    private BigDecimal avg;
    private LocalDateTime gmtCreate;
}
