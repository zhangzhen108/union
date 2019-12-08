package com.union.dto.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class OrderParamDTO implements Serializable {
    private static final long serialVersionUID = 8722096337718605472L;
    /**
     *  0-- 全部 1--待付款 2-- 代发货 3--已发货 4--待评价
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
