package com.union.trans;

import com.union.domain.ChannelDO;
import com.union.dto.result.ChannelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelTrans extends EntityMapper<ChannelDTO,ChannelDO>{


}
