package com.union.controller;

import com.union.common.ErrorEnum;
import com.union.common.R;
import com.union.dto.param.CategoryParamDTO;
import com.union.dto.param.ChannelParamDTO;
import com.union.dto.result.CategoryDTO;
import com.union.dto.result.ChannelDTO;
import com.union.service.CategoryService;
import com.union.service.ChannelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/channel/")
public class ChannelController {

    @Resource
    ChannelService channelService;
    @GetMapping("queryList")
    public R queryList(ChannelParamDTO channelParamDTO){
        List<ChannelDTO> channelDTOList= channelService.queryList(channelParamDTO);
        return R.creatR(channelDTOList, ErrorEnum.SUSSESS);
    }
}
