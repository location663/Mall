/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/18
 * Time  下午 7:50
 */

package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.wx.WxGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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


}
