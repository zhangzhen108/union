package com.union.dto.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO implements Serializable {


    private static final long serialVersionUID = -691581100453827074L;
    /**
     * 第三方id
     */
    private Long thirdId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 推销url
     */
    private String url;
    /**
     * 商品描述
     */
    private String desc;
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
     * 优惠券
     */
    private List<String> smallImages;
    /**
     * 券面值
     */
    private BigDecimal couponAmount;
    /**
     * 渠道
     */
    private ChannelDTO channelDTO;
}
