package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.RegionVO;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.service.admin.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class MarketController {

    @Autowired
    MarketService marketService;

    @RequestMapping("region/list")
    public BaseReqVo region(){
        BaseReqVo baseReqVo = new BaseReqVo();

        List<RegionVO> regionVOS = marketService.selectRegions();


        baseReqVo.setData(regionVOS);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("brand/list")
    public BaseReqVo brandList(RequestPageDTO pageDTO){
        Map<String, Object> stringObjectMap = marketService.selectBrand(pageDTO);

        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        baseReqVo.setData(stringObjectMap);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
