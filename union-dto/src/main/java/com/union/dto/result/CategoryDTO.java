package com.union.dto.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class CategoryDTO implements Serializable{
    private static final long serialVersionUID = 4037362673667038799L;
    private Long id;

    private String name;

    private Long channelId;

    private Long sort;

    private String url;

    private Long thirdid;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;

}