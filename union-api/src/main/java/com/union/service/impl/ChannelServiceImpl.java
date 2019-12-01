package com.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.common.CommonStatusEnum;
import com.union.domain.CategoryDO;
import com.union.domain.ChannelDO;
import com.union.dto.param.ChannelParamDTO;
import com.union.dto.result.ChannelDTO;
import com.union.mapper.ChannelDOMapper;
import com.union.service.ChannelService;
import com.union.trans.ChannelTrans;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelDOMapper,ChannelDO> implements ChannelService {
    @Resource
    ChannelDOMapper channelDOMapper;
    @Resource
    ChannelTrans channelTrans;
    @Value("${info.app.img-url}")
    String imgUrl;
    @Override
    public List<ChannelDTO> queryList(ChannelParamDTO channelParamDTO) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status", CommonStatusEnum.enable.getStatus());
        queryWrapper.orderByAsc("sort");
        if(channelParamDTO!=null&&channelParamDTO.getLimitNum()!=null){
            queryWrapper.last("limit ".concat(channelParamDTO.getLimitNum().toString()));
        }
        List<ChannelDO> channelDOList=super.list(queryWrapper);
        if(CollectionUtils.isEmpty(channelDOList)){
            return channelTrans.toDto(channelDOList);
        }
        for (ChannelDO channelDO:channelDOList) {
            channelDO.setUrl(imgUrl.concat(channelDO.getUrl()));
        }
        return channelTrans.toDto(channelDOList);
    }
}
