package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.ConfigService;
import com.wangdao.mall.service.util.ConfigRegExUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions(value = {"admin:config:mall:list"})
    public BaseReqVo getMallConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getMallConfig();
        return baseReqVo;
    }

    /**
     * 修改商场配置
     */
    @RequestMapping(value = "mall", method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:config:mall:updateConfigs"})
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
    @RequiresPermissions(value = {"admin:config:express:list"})
    public BaseReqVo getExpressConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getExpressConfig();
        return baseReqVo;
    }

    /**
     * 修改运费配置
     * post方法
     */
    @RequestMapping(value = "express", method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:config:express:updateConfigs"})
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
    @RequiresPermissions(value = {"admin:config:order:list"})
    public BaseReqVo getOrderConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getOrderConfig();
        return baseReqVo;
    }

    /**
     * 修改订单配置
     * post方法
     */
    @RequestMapping(value = "order", method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:config:order:updateConfigs"})
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
    @RequiresPermissions(value = {"admin:config:wx:list"})
    public BaseReqVo getWxConfig(){
        BaseReqVo<Map<String,Object>> baseReqVo = configService.getWxConfig();
        return baseReqVo;
    }

    /**
     * 修改小程序配置
     * post方法
     */
    @RequestMapping(value = "wx", method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:config:wx:updateConfigs"})
    public BaseReqVo setWxConfig(@RequestBody Map<String, Object> map){
        int configRegEx = ConfigRegExUtils.isConfigRegEx(map);
        if (configRegEx != 0){
            return new BaseReqVo(null,null,configRegEx);
        }
        BaseReqVo baseReqVo = configService.setWxConfig(map);
        return baseReqVo;
    }
}
