package com.union.trans;

import com.union.domain.CategoryDO;
import com.union.domain.OrderDO;
import com.union.dto.result.CategoryDTO;
import com.union.dto.result.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderTrans extends EntityMapper<OrderDTO, OrderDO>{
}
