package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.RegionVO;
import com.wangdao.mall.bean.RequestPageDTO;

import java.util.List;
import java.util.Map;

public interface MarketService {
    List<RegionVO> selectRegions();

    Map<String, Object> selectBrand(RequestPageDTO pageDTO);
}
