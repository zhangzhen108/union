package com.union.dto.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class CategoryParamDTO implements Serializable{


    private static final long serialVersionUID = -6838897531923137478L;

    private Integer limitNum;

    private Long channelId;
}
