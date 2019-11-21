package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.AddressDO;
import com.wangdao.mall.bean.RegionDO;

import java.util.List;

public interface WxAddressService {
    List<AddressDO> listAddress();

    AddressDO selectById(Integer id);

    int updateAddress(AddressDO addressDO);

    List<RegionDO> listByPid(Integer id);

    int deleteByPid(Integer id);
}
