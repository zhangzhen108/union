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
    private Long categoryThirdId;
    /**
     * 排序类型
     */
    private Integer sortType;
    /**
     * 来源code
     */
    private String channelCode;
    /**
     * 1--正序 2--倒叙
     */
    private Integer sort;
    /**
     * 排序字段
     */
    private String sortFiled;
    /**
     * 是否爆款 1--爆款 2--非爆款
     */
    private Integer isHot;
}
