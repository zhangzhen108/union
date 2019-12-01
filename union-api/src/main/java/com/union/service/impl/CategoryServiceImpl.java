package com.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.common.BusinessErrorException;
import com.union.common.CommonStatusEnum;
import com.union.common.ErrorEnum;
import com.union.domain.CategoryDO;
import com.union.domain.ChannelDO;
import com.union.dto.param.CategoryParamDTO;
import com.union.dto.result.CategoryDTO;
import com.union.mapper.CategoryDOMapper;
import com.union.service.CategoryService;
import com.union.trans.CategoryTrans;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDOMapper,CategoryDO> implements CategoryService{

    @Resource
    CategoryDOMapper categoryDOMapper;
    @Resource
    CategoryTrans categoryTrans;
    @Value("${info.app.img-url}")
    String imgUrl;
    @Override
    public List<CategoryDTO> queryList(CategoryParamDTO categoryParamDTO) {
        if(categoryParamDTO==null){
            throw new BusinessErrorException(ErrorEnum.CHECK_ERROR);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        if(categoryParamDTO.getChannelId()!=null){
            queryWrapper.eq("channel_id", categoryParamDTO.getChannelId());
        }
        queryWrapper.eq("status", CommonStatusEnum.enable.getStatus());
        queryWrapper.orderByAsc("sort");
        if(categoryParamDTO.getLimitNum()!=null){
            queryWrapper.last("limit ".concat(categoryParamDTO.getLimitNum().toString()));
        }
        List<CategoryDO> categoryDOList=super.list(queryWrapper);
        if(CollectionUtils.isEmpty(categoryDOList)){
            return categoryTrans.toDto(categoryDOList);
        }
        for (CategoryDO categoryDO:categoryDOList) {
            categoryDO.setUrl(imgUrl.concat(categoryDO.getUrl()));
        }
        return categoryTrans.toDto(categoryDOList);
    }
}
