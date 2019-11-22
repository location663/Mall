package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.AddressDOMapper;
import com.wangdao.mall.mapper.RegionDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WxAddressServiceImpl implements WxAddressService {

    @Autowired
    AddressDOMapper addressDOMapper;

    @Autowired
    RegionDOMapper regionDOMapper;

    /**
     * 用户的收货地址
     * @return
     */
    @Override
    public List<AddressDO> listAddress() {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        AddressDOExample addressDOExample = new AddressDOExample();
        addressDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId());
        List<AddressDO> addressDOS = addressDOMapper.selectByExample(addressDOExample);
        return addressDOS;
    }

    /**
     * 地址详情
     * @param id
     * @return
     */
    @Override
    public AddressDO selectById(Integer id) {
        AddressDO addressDO = addressDOMapper.selectByPrimaryKey(id);
        RegionDO regionDO1 = regionDOMapper.selectByPrimaryKey(addressDO.getProvinceId());
        RegionDO regionDO2 = regionDOMapper.selectByPrimaryKey(addressDO.getCityId());
        RegionDO regionDO3 = regionDOMapper.selectByPrimaryKey(addressDO.getAreaId());
        addressDO.setProvinceName(regionDO1.getName());
        if (regionDO2 != null) {
            addressDO.setCityName(regionDO2.getName());
        }
        if (regionDO3 != null) {
            addressDO.setAreaName(regionDO3.getName());
        }
        return addressDO;
    }

    /**
     * 修改地址，新增地址
     * @param addressDO
     * @return
     */
    @Override
    public int updateAddress(AddressDO addressDO) {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        addressDO.setUpdateTime(new Date());
        int id;
        if (addressDO.getId() != 0) {
            AddressDOExample addressDOExample = new AddressDOExample();
            addressDOExample.createCriteria().andUserIdEqualTo(userDO.getId()).andIdEqualTo(addressDO.getId());
            addressDOMapper.updateByExampleSelective(addressDO, addressDOExample);
            id =  addressDO.getId();
        } else {
            addressDO.setAddTime(new Date());
            addressDO.setUserId(userDO.getId());
            addressDOMapper.insertSelective(addressDO);
            id = addressDOMapper.selectLastInsertId();
            addressDO.setId(id);
        }
        if (addressDO.getIsDefault()){
            addressDOMapper.updateDefaultByUidAndId(userDO.getId(), addressDO.getId());
        }
        return id;
    }

    /**
     * 根据pid获取地区
     * @param pid
     * @return
     */
    @Override
    public List<RegionDO> listByPid(Integer pid) {
        RegionDOExample regionDOExample = new RegionDOExample();
        regionDOExample.createCriteria().andPidEqualTo(pid);
        List<RegionDO> regionDOList = regionDOMapper.selectByExample(regionDOExample);
        return regionDOList;
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    @Override
    public int deleteByPid(Integer id) {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        int res = addressDOMapper.updateDeletedById(userDO.getId(), id);
        return res;
    }
}
