/**
 * 优惠券模块
 * User: Jql
 * Date  2019/11/20
 * Time  下午 8:02
 */

package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.wx.WxCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxCouponController {

    @Autowired
    WxCouponService wxCouponService;


    /**
     * 优惠券列表  (每页只显示10张优惠券)(有desc关键字)
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("coupon/list")
    public BaseReqVo couponList(Integer page,Integer size){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxCouponService.queryCouponList(page,size);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }



    /**
     * 领取优惠券
     * @param map
     * @return
     */
    @RequestMapping("coupon/receive")
    public BaseReqVo couponReceive(@RequestBody Map map){  //map里有 couponId
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i=wxCouponService.couponReceive(map);

        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
