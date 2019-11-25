package com.union.dto.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDTO implements Serializable {


    private static final long serialVersionUID = -691581100453827074L;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String imgUrl;
    /**
     * 单买商品价格
     */
    private BigDecimal price;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 商品来源
     */
    private Integer source;
    /**
     * 优惠券
     */
    private CouponDTO couponDTO;
}
