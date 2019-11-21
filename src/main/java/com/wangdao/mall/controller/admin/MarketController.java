package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.service.admin.MarketService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

    /**
     * 区域列表
     * @return
     */
    @RequestMapping("region/list")
    public BaseReqVo region(){
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
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
    public BaseReqVo insertStorage(MultipartFile file){
        StorageDO storageDO = marketService.insertStorage(file);
        BaseReqVo baseReqVo = new BaseReqVo(storageDO, "成功", 0);
        return baseReqVo;
    }

    /**
     * 品牌删除
     * @param brandDO
     * @return
     */
    @RequestMapping("brand/delete")
    public BaseReqVo brandDelete(@RequestBody BrandDO brandDO){
        int result = marketService.deleteBrand(brandDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    /**
     * 一级货物列表
     * @return
     */
    @RequestMapping("category/list")
    public BaseReqVo categoryList1(){
        List<CategoryDO> categoryDOList = marketService.listCategory();
        BaseReqVo baseReqVo = new BaseReqVo(categoryDOList, "成功", 0);
        return baseReqVo;
    }

    /**
     * 一级货物列表
     * @return
     */
    @RequestMapping("category/l1")
    public BaseReqVo categoryL1(){
        List<CategoryVO> categoryVOList = marketService.listCategory1();
        BaseReqVo baseReqVo = new BaseReqVo(categoryVOList, "成功", 0);
        return baseReqVo;
    }

    /**
     * 创建货物
     * @param categoryDO
     * @return
     */
    @RequestMapping("category/create")
    public BaseReqVo categoryCreate(@RequestBody CategoryDO categoryDO){
        CategoryDO categoryDO1 = marketService.insertCategory(categoryDO);
        BaseReqVo baseReqVo = new BaseReqVo(categoryDO1, "成功", 0);
        return baseReqVo;
    }

    /**
     * 更新货物
     * @param categoryDO
     * @return
     */
    @RequestMapping("category/update")
    public BaseReqVo categoryUpdate(@RequestBody CategoryDO categoryDO){
        int i = marketService.updateCategory(categoryDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    /**
     * 删除货物
     * @param categoryDO
     * @return
     */
    @RequestMapping("category/delete")
    public BaseReqVo categoryDelete(@RequestBody CategoryDO categoryDO){
        int result = marketService.deleteCategory(categoryDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    /**
     * 订单列表查询
     * @param requestPageDTO
     * @return
     */
    @RequestMapping("order/list")
    public BaseReqVo orderList(RequestPageDTO requestPageDTO){
        Map orderDOMap = marketService.listOrder(requestPageDTO);
        BaseReqVo baseReqVo = new BaseReqVo(orderDOMap, "成功", 0);
        return baseReqVo;
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @RequestMapping("order/detail")
    public BaseReqVo orderDetail(Integer id){
        Map map = marketService.detailOrder(id);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
    }

    /**
     * 通用问题列表
     * @param requestPageDTO
     * @return
     */
    @RequestMapping("issue/list")
    public BaseReqVo issueList(RequestPageDTO requestPageDTO){
        Map issueDOMap = marketService.listIssue(requestPageDTO);
        BaseReqVo baseReqVo = new BaseReqVo(issueDOMap, "成功", 0);
        return baseReqVo;
    }

    /**
     * 创建通用问题
     * @param issueDO
     * @return
     */
    @RequestMapping("issue/create")
    public BaseReqVo issueCreate(@RequestBody IssueDO issueDO){
        IssueDO issueDO1 = marketService.insertIssue(issueDO);
        BaseReqVo baseReqVo = new BaseReqVo(issueDO1, "成功", 0);
        return baseReqVo;
    }

    /**
     * 修改通用问题
     * @param issueDO
     * @return
     */
    @RequestMapping("issue/update")
    public BaseReqVo issueUpdate(@RequestBody IssueDO issueDO){
        IssueDO issueDO1 = marketService.updateIssue(issueDO);
        BaseReqVo baseReqVo = new BaseReqVo(issueDO1, "成功", 0);
        return baseReqVo;
    }

    /**
     * 删除通用问题
     * @param issueDO
     * @return
     */
    @RequestMapping("issue/delete")
    public BaseReqVo issueDelete(@RequestBody IssueDO issueDO){
        int res = marketService.deleteIssue(issueDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    /**
     * 关键词列表
     * @param requestPageDTO
     * @return
     */
    @RequestMapping("keyword/list")
    public BaseReqVo keywordList(RequestPageDTO requestPageDTO){
        Map keywordMap = marketService.listKeyword(requestPageDTO);
        BaseReqVo baseReqVo = new BaseReqVo(keywordMap, "成功", 0);
        return baseReqVo;
    }

    /**
     * 创建关键词
     * @param keywordDO
     * @return
     */
    @RequestMapping("keyword/create")
    public BaseReqVo keywordCreate(@RequestBody KeywordDO keywordDO){
        KeywordDO keywordDO1 = marketService.insertKeyword(keywordDO);
        BaseReqVo baseReqVo = new BaseReqVo(keywordDO1, "成功", 0);
        return baseReqVo;
    }

    /**
     * 删除关键词
     * @param keywordDO
     * @return
     */
    @RequestMapping("keyword/delete")
    public BaseReqVo keywordDelete(@RequestBody KeywordDO keywordDO){
        int res = marketService.deleteKeyword(keywordDO);
        BaseReqVo baseReqVo = new BaseReqVo(null, "成功", 0);
        return baseReqVo;
    }

    /**
     * 修改关键词
     * @param keywordDO
     * @return
     */
    @RequestMapping("keyword/update")
    public BaseReqVo keywordUpdate(@RequestBody KeywordDO keywordDO){
        KeywordDO keywordDO1 = marketService.updateKeyword(keywordDO);
        BaseReqVo baseReqVo = new BaseReqVo(keywordDO1, "成功", 0);
        return baseReqVo;
    }

//    @RequestMapping("order/refund")
//    public BaseReqVo refundOrder(@RequestBody Map<String, Integer> map){
//        Integer orderId = map.get("orderId");
//        Integer refundMoney = map.get("refundMoney");
//        int refund = marketService.refundOrder(orderId, refundMoney);
//    }

}
