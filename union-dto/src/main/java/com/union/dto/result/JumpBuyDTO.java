package com.union.dto.result;

import lombok.Data;

import java.io.Serializable;
@Data
public class JumpBuyDTO extends ChannelDTO implements Serializable{
    private static final long serialVersionUID = -6060653220156299029L;
    /**
     * 1 复制 2跳转
     */
    private Integer jumType;

    private String appid;

    private String path;

    private String data;

    private String version;
}
