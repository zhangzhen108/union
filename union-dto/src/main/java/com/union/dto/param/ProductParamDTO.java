package com.union.dto.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class ProductParamDTO implements Serializable {
    private static final long serialVersionUID = -5392174681762813701L;
    /**
     * 查询关键词
     */
    private String keyword;
    /**
     * 类目id
     */
    private Long categoryId;
    /**
     * 排序类型
     */
    private Integer sortType;
    /**
     * 来源code
     */
    private String code;
}
