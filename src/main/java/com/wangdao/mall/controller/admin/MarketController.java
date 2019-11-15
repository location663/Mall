package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.service.admin.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class MarketController {

    @Autowired
    MarketService marketService;

    @RequestMapping("region/list")
    public BaseReqVo region(){
        List<RegionVO> regionVOS = marketService.selectRegions();
        BaseReqVo baseReqVo = new BaseReqVo(regionVOS, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("brand/list")
    public BaseReqVo brandList(RequestPageDTO pageDTO){
        Map<String, Object> stringObjectMap = marketService.selectBrand(pageDTO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(stringObjectMap, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("brand/update")
    public BaseReqVo brandUpdate(@RequestBody BrandDO brandDO){
        BrandDO brandDO1 = marketService.updateBrandById(brandDO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(brandDO1, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("brand/create")
    public BaseReqVo brandCreate(@RequestBody BrandDO brandDO){

        BrandDO brandDO1 = marketService.insertBrand(brandDO);

        BaseReqVo baseReqVo = new BaseReqVo(brandDO1, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("storage/create")
    public BaseReqVo insertStorage(MultipartFile file){
        StorageDO storageDO = marketService.insertStorage(file);
        BaseReqVo baseReqVo = new BaseReqVo(storageDO, "成功", 0);
        return baseReqVo;
    }
}
