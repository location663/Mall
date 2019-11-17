package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.ConfigService;
import com.wangdao.mall.service.util.ConfigRegExUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("admin/config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    /**
     * 获取商场配置
     */
    @RequestMapping(value = "mall", method = RequestMethod.GET)
    public BaseReqVo getMallConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getMallConfig();
        return baseReqVo;
    }

    /**
     * 修改商场配置
     */
    @RequestMapping(value = "mall", method = RequestMethod.POST)
    public BaseReqVo setMallConfig(@RequestBody Map<String, Object> map){
        int configRegEx = ConfigRegExUtils.isConfigRegEx(map);
        if (configRegEx != 0){
            return new BaseReqVo(null,null,configRegEx);
        }
        BaseReqVo baseReqVo = configService.setMallConfig(map);
        return baseReqVo;
    }

    /**
     * 获取运费配置
     * get方法
     */
    @RequestMapping(value = "express", method = RequestMethod.GET)
    public BaseReqVo getExpressConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getExpressConfig();
        return baseReqVo;
    }

    /**
     * 修改运费配置
     * post方法
     */
    @RequestMapping(value = "express", method = RequestMethod.POST)
    public BaseReqVo setExpressConfig(@RequestBody Map<String, Object> map){
        int configRegEx = ConfigRegExUtils.isConfigRegEx(map);
        if (configRegEx != 0){
            return new BaseReqVo(null,null,configRegEx);
        }
        BaseReqVo baseReqVo = configService.setExpressConfig(map);
        return baseReqVo;
    }

    /**
     * 获取订单配置
     * get方法
     */
    @RequestMapping(value = "order", method = RequestMethod.GET)
    public BaseReqVo getOrderConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getOrderConfig();
        return baseReqVo;
    }

    /**
     * 修改订单配置
     * post方法
     */
    @RequestMapping(value = "order", method = RequestMethod.POST)
    public BaseReqVo setOrderConfig(@RequestBody Map<String, Object> map){
        int configRegEx = ConfigRegExUtils.isConfigRegEx(map);
        if (configRegEx != 0){
            return new BaseReqVo(null,null,configRegEx);
        }
        BaseReqVo baseReqVo = configService.setOrderConfig(map);
        return baseReqVo;
    }

    /**
     * 获取小程序配置
     * get方法
     */
    @RequestMapping(value = "wx", method = RequestMethod.GET)
    public BaseReqVo getWxConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getWxConfig();
        return baseReqVo;
    }

    /**
     * 修改小程序配置
     * post方法
     */
    @RequestMapping(value = "wx", method = RequestMethod.POST)
    public BaseReqVo setWxConfig(@RequestBody Map<String, Object> map){
        int configRegEx = ConfigRegExUtils.isConfigRegEx(map);
        if (configRegEx != 0){
            return new BaseReqVo(null,null,configRegEx);
        }
        BaseReqVo baseReqVo = configService.setWxConfig(map);
        return baseReqVo;
    }
}
