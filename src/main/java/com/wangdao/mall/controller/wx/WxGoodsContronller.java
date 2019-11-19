/**
 * wx端所有goods接口
 * User: Jql
 * Date  2019/11/18
 * Time  下午 7:50
 */

package com.wangdao.mall.controller.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.GoodsDO;
import com.wangdao.mall.bean.GoodsDOExample;
import com.wangdao.mall.service.wx.WxGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("wx")
public class WxGoodsContronller {

    @Autowired
    WxGoodsService wxGoodsService;


    /**
     * WX统计商品总数
     * @return
     */
    @RequestMapping("goods/count")
    public BaseReqVo goodsCount() {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxGoodsService.queryGoodsCount();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**
     * WX获取获取显示(搜索keyword)(某类目)(某品牌)(是新品)(是热卖)的商品列表
     * 输入一串空格，没有显示商品，但是返回得成功
     * @param keyword
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping("goods/list")
    public BaseReqVo wxGoodsList(String keyword,Integer categoryId,Integer brandId,Boolean isNew,Boolean isHot,Integer page,Integer size,String sort,String order){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = new HashMap<>();
        map = wxGoodsService.queryWxGoodsList(keyword,categoryId,brandId,isNew,isHot,page,size,sort,order);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**
     * WX获得分类数据(显示某一级类目下所有二级类目)
     * @param id
     * @return
     */
    @RequestMapping("goods/category")
    public BaseReqVo wxGoodsCategory(Integer id){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxGoodsService.queryWxGoodsCategory(id);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**
     * 获取商品详情
     * "groupon": [],//团购商品列表  和 "shareImage": "",这两个属性不知道该返回啥，需改进
     * "issue": []    //所有的问答  //我是直接返回数据表的所有issue对象，不知道是否正确(老师项目也是全返回)
     * "userHasCollect": 0,    //返回的这个属性，0为没收藏关注，1为收藏关注，需要注意在用户是否登录状态查看这个属性
     * @param id
     * @return
     */
    @RequestMapping("goods/detail")
    public BaseReqVo wxGoodsDetail(Integer id){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxGoodsService.queryWxGoodsDetail(id);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }



    /**
     * 商品详情页的关联商品(广告)
     * 该商品详情页的关联商品最多只显示该商品同类目下6个商品，顺序不清楚，可以再次显示该商品自己
     * @param id
     * @return
     */
    @RequestMapping("goods/related")
    public BaseReqVo goodsRelated(Integer id){   //id为该商品详情页的商品id
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxGoodsService.queryWxGoodsRelated(id);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}
