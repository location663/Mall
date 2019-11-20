package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.AddressDO;
import com.wangdao.mall.bean.AddressDOExample;
import com.wangdao.mall.mapper.AddressDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxAddressServiceImpl implements WxAddressService {

    @Autowired
    AddressDOMapper addressDOMapper;

    /**
     * 用户的收货地址
     * @return
     */
    @Override
    public List<AddressDO> listAddress() {
        SecurityUtils.getSubject().getPrincipal();
        AddressDOExample addressDOExample = new AddressDOExample();
        addressDOExample.createCriteria().andDeletedEqualTo(false);
        List<AddressDO> addressDOS = addressDOMapper.selectByExample(addressDOExample);
        return addressDOS;
    }
}
