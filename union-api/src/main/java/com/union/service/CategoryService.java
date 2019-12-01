package com.union.service;

import com.union.domain.CategoryDO;
import com.union.dto.param.CategoryParamDTO;
import com.union.dto.result.CategoryDTO;

import java.util.List;
import java.util.Locale;

public interface CategoryService {
    /**
     * 批量查询
     * @param categoryParamDTO
     * @return
     */
    List<CategoryDTO> queryList(CategoryParamDTO categoryParamDTO);
}
