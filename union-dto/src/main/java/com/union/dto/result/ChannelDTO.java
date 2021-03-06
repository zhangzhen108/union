package com.union.dto.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ChannelDTO implements Serializable {
    private static final long serialVersionUID = -5215523865225884437L;
    private Long id;

    private String url;

    private String code;

    private Long sort;

    private String msg;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;
}