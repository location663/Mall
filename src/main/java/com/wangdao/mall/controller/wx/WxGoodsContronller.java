/**
 * Created by IntelliJ IDEA.
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
     * WX获取(搜索)商品列表
     * 输入一串空格，没有显示商品，但是返回得成功
     * @param keyword
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping("goods/list")
    public BaseReqVo wxGoodsList(String keyword,Integer categoryId,Integer page,Integer size,String sort,String order){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        HashMap<String, Object> map = new HashMap<>();
        if (categoryId!=0){

        }else if (categoryId==0){
            map = wxGoodsService.queryWxGoodsListBykeyword(keyword,page,size,sort,order);
        }


        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        return baseReqVo;
    }

}