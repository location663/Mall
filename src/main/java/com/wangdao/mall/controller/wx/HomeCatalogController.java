package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.wx.HomeCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx")
public class HomeCatalogController {

    @Autowired
    HomeCatalogService homeCatalogService;

    /**
     * 首页数据接口
     */
    @RequestMapping("home/index")
    public BaseReqVo getHomeIndex(){
        Map<String, Object> map = homeCatalogService.getHomeIndex();
        return new BaseReqVo<>(map,"成功",0);
    }
}
