package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.service.admin.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class MarketController {

    @Autowired
    MarketService marketService;

    /**
     * 区域列表
     * @return
     */
    @RequestMapping("region/list")
    public BaseReqVo region(){
        List<RegionVO> regionVOS = marketService.selectRegions();
        BaseReqVo baseReqVo = new BaseReqVo(regionVOS, "成功", 0);
        return baseReqVo;
    }

    /**
     * 品牌列表及模糊查询
     * @param pageDTO
     * @return
     */
    @RequestMapping("brand/list")
    public BaseReqVo brandList(RequestPageDTO pageDTO){
        Map<String, Object> stringObjectMap = marketService.selectBrand(pageDTO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(stringObjectMap, "成功", 0);
        return baseReqVo;
    }

    /**
     * 商场品牌更新
     * @param brandDO
     * @return
     */
    @RequestMapping("brand/update")
    public BaseReqVo brandUpdate(@RequestBody BrandDO brandDO){
        BrandDO brandDO1 = marketService.updateBrandById(brandDO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(brandDO1, "成功", 0);
        return baseReqVo;
    }

    /**
     * 新建商场商品品牌
     * @param brandDO
     * @return
     */
    @RequestMapping("brand/create")
    public BaseReqVo brandCreate(@RequestBody BrandDO brandDO){
        BrandDO brandDO1 = marketService.insertBrand(brandDO);
        BaseReqVo baseReqVo = new BaseReqVo(brandDO1, "成功", 0);
        return baseReqVo;
    }

    /**
     * 图片上传
     * @param file
     * @return
     */
    @RequestMapping("storage/create")
    public BaseReqVo insertStorage(MultipartFile file, HttpSession session){
        String realPath = session.getServletContext().getRealPath("");
        StorageDO storageDO = marketService.insertStorage(file);
        BaseReqVo baseReqVo = new BaseReqVo(storageDO, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("brand/delete")
    public BaseReqVo brandDelete(@RequestBody BrandDO brandDO){
        int result = marketService.deleteBrand(brandDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("category/list")
    public BaseReqVo categoryList1(){
        List<CategoryDO> categoryDOList = marketService.listCategory();
        BaseReqVo baseReqVo = new BaseReqVo(categoryDOList, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("category/l1")
    public BaseReqVo categoryL1(){
        List<CategoryVO> categoryVOList = marketService.listCategory1();
        BaseReqVo baseReqVo = new BaseReqVo(categoryVOList, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("category/create")
    public BaseReqVo categoryCreate(@RequestBody CategoryDO categoryDO){
        CategoryDO categoryDO1 = marketService.insertCategory(categoryDO);
        BaseReqVo baseReqVo = new BaseReqVo(categoryDO1, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("category/update")
    public BaseReqVo categoryUpdate(@RequestBody CategoryDO categoryDO){
        int i = marketService.updateCategory(categoryDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("category/delete")
    public BaseReqVo categoryDelete(@RequestBody CategoryDO categoryDO){
        int result = marketService.deleteCategory(categoryDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("order/list")
    public BaseReqVo orderList(@RequestBody OrderDO orderDO){
        return null;
    }
}
