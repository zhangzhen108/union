package com.union.controller;

import com.union.common.ErrorEnum;
import com.union.common.R;
import com.union.dto.param.CategoryParamDTO;
import com.union.dto.result.CategoryDTO;
import com.union.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {
    @Resource
    CategoryService categoryService;
    @GetMapping("queryList")
    public R queryList(CategoryParamDTO categoryParamDTO){
        List<CategoryDTO> categoryDTOList= categoryService.queryList(categoryParamDTO);
        return R.creatR(categoryDTOList, ErrorEnum.SUCCESS);
    }
}
