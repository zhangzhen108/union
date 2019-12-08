package com.union.dto.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class OrderDTO implements Serializable {

    private Long id;

    private Long channelId;

    private String productTitle;

    private Long productId;

    private Long productNum;

    private Integer orderStatus;

    private String pid;

    private Long productPrice;

    private String productImgUrl;

    private Long orderAmount;

    private Date orderCreateTime;

    private Date orderPayTime;

    private String orderSn;

    private Long promotionAmount;

    private Long promotionRate;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;
}
