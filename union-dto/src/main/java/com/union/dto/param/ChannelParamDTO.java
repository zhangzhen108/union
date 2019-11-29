package com.union.dto.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class ChannelParamDTO implements Serializable{
    private static final long serialVersionUID = -4852081136886305467L;

    private String id;

    private String code;

    private String msg;
}
