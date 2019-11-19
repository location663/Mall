package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.BrandDO;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.service.admin.MarketService;
import com.wangdao.mall.service.wx.WxBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxBrandController {

    @Autowired
    MarketService marketService;

    @Autowired
    WxBrandService brandService;

    /**
     * 品牌列表
     * @param pageDTO
     * @return
     */
    @RequestMapping("brand/list")
    public BaseReqVo brandList(RequestPageDTO pageDTO){
        pageDTO.setLimit(pageDTO.getSize());
//        Map<String, Object> map = marketService.selectBrand(pageDTO);
        Map<String, Object> map = brandService.listBrand(pageDTO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }

    /**
     * 品牌详情
     * @param id
     * @return
     */
    @RequestMapping("brand/detail")
    public BaseReqVo brandDetail(Integer id){
        BrandDO brandDO = brandService.selectById(id);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("brand", brandDO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }
}
