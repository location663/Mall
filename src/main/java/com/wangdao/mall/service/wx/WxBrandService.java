package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.BrandDO;
import com.wangdao.mall.bean.RequestPageDTO;

import java.util.Map;

public interface WxBrandService {

    BrandDO selectById(Integer id);

    Map<String, Object> listBrand(RequestPageDTO pageDTO);
}
