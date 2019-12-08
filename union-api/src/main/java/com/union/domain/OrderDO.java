package com.union.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("order_info")
public class OrderDO {
    @TableId(type = IdType.AUTO)
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