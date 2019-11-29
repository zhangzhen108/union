package com.union.dto.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class JumpBuyParamDTO implements Serializable {
    private static final long serialVersionUID = 4024343224373776969L;

    private Long id;

    private String name;

    private String url;

    private String imgUrl;

    private String productId;

    private String channelCode;
}
