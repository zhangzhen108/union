package com.union.service;

import com.union.dto.param.CategoryParamDTO;
import com.union.dto.param.ChannelParamDTO;
import com.union.dto.result.CategoryDTO;
import com.union.dto.result.ChannelDTO;

import java.util.List;

public interface ChannelService {
    /**
     * 批量查询
     * @param channelParamDTO
     * @return
     */
    List<ChannelDTO> queryList(ChannelParamDTO channelParamDTO);
}
