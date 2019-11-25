package com.union.dto.result;

import lombok.Data;

@Data
public class TbProductDetailDTO extends ProductDetailDTO {
    private static final long serialVersionUID = 5477062301922913853L;
    /**
     * 淘口令
     */
    private String tpwd;

}
