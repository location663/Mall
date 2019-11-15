package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.BrandDO;
import com.wangdao.mall.bean.RegionVO;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.bean.StorageDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MarketService {
    List<RegionVO> selectRegions();

    Map<String, Object> selectBrand(RequestPageDTO pageDTO);

    BrandDO updateBrandById(BrandDO brandDO);

    BrandDO insertBrand(BrandDO brandDO);

    StorageDO insertStorage(MultipartFile file);
}
