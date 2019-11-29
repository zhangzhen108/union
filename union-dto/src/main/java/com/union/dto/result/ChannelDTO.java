package com.union.dto.result;

import lombok.Data;

import java.io.Serializable;
@Data
public class ChannelDTO implements Serializable {

    private static final long serialVersionUID = -4992204702779025522L;

    private String id;

    private String code;

    private String msg;
}
