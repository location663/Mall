package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MarketService {
    List<RegionVO> selectRegions();

    Map<String, Object> selectBrand(RequestPageDTO pageDTO);

    BrandDO updateBrandById(BrandDO brandDO);

    BrandDO insertBrand(BrandDO brandDO);

    StorageDO insertStorage(MultipartFile file);

    int deleteBrand(BrandDO brandDO);

    List<CategoryDO> listCategory();

    List<CategoryVO> listCategory1();

    CategoryDO insertCategory(CategoryDO categoryDO);

    int updateCategory(CategoryDO categoryDO);

    int deleteCategory(CategoryDO categoryDO);
}
