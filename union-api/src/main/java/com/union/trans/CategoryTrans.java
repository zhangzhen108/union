package com.union.trans;

import com.union.domain.CategoryDO;
import com.union.dto.result.CategoryDTO;
import org.mapstruct.Mapper;

import java.util.Locale;
@Mapper(componentModel = "spring")
public interface CategoryTrans extends EntityMapper<CategoryDTO,CategoryDO> {
}
