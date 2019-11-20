package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.AddressDO;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.wx.WxAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
