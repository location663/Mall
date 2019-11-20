package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.AddressDO;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.RegionDO;
import com.wangdao.mall.service.wx.WxAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxAddressController {

    @Autowired
    WxAddressService addressService;

    /**
     *
     * @return
     */
    @RequestMapping("address/list")
    public BaseReqVo addressList(){
        List<AddressDO> listAddress = addressService.listAddress();
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(listAddress, "成功", 0);
        return baseReqVo;
    }

    /**
     * 详细地址
     * @param id
     * @return
     */
    @RequestMapping("address/detail")
    public BaseReqVo addressDetail(Integer id){
        AddressDO addressDO = addressService.selectById(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(addressDO, "成功", 0);
        return baseReqVo;
    }

    /**
     * 修改地址
     * @param addressDO
     * @return
     */
    @RequestMapping("address/save")
    public BaseReqVo addressDetail(@RequestBody AddressDO addressDO){
        int addressId = addressService.updateAddress(addressDO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(addressId, "成功", 0);
        return baseReqVo;
    }

    /**
     * 根据pid获取分区
     * @param pid
     * @return
     */
    @RequestMapping("region/list")
    public BaseReqVo regionList(Integer pid){
        List<RegionDO> regionDOList = addressService.listByPid(pid);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(regionDOList, "成功", 0);
        return baseReqVo;
    }

    /**
     * 删除收货地址
     * @param map
     * @return
     */
    @RequestMapping("address/delete")
    public BaseReqVo addressDelete(@RequestBody Map<String, Integer> map){
        int res = addressService.deleteByPid(map.get("id"));
        if (res == 1) {
            BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "删除失败", 500);
        return baseReqVo;
    }
}
